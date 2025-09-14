package net.tuchnyak.service;

import net.tuchnyak.db.Transactional;
import net.tuchnyak.dto.Page;
import net.tuchnyak.dto.PostListItem;
import net.tuchnyak.exception.PostUploadException;
import net.tuchnyak.exception.post.*;
import net.tuchnyak.model.blog.Post;
import net.tuchnyak.repository.PostRepository;
import net.tuchnyak.text.ParsedInfo;
import net.tuchnyak.text.TextConverter;
import net.tuchnyak.text.TextConverterFactory;
import net.tuchnyak.util.Logging;
import net.tuchnyak.util.TimestampSqlNow;
import net.tuchnyak.uuid.UuidGenerator;
import net.tuchnyak.uuid.UuidGeneratorFactory;
import rife.database.DbQueryManager;

import java.util.*;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostUploadServiceImpl implements PostUploadService, Logging {

    public static final String ABOUT_SLUG = "/about";

    private final TextConverter textConverter;
    private final UuidGenerator idGenerator;
    private final PostRepository postRepository;
    private final DbQueryManager dbManager;

    public PostUploadServiceImpl(PostRepository postRepository) {
        this(
                TextConverterFactory.getMarkdownConverter(),
                UuidGeneratorFactory.getV7TimeBasedUuidGenerator(),
                postRepository
        );
    }

    public PostUploadServiceImpl(TextConverter textConverter, UuidGenerator idGenerator, PostRepository postRepository) {
        this.textConverter = textConverter;
        this.idGenerator = idGenerator;
        this.postRepository = postRepository;
        this.dbManager = ((Transactional) postRepository).getDbQueryManager();
    }

    @Override
    public Post uploadPost(String rawPostContent) {
        if (rawPostContent == null || rawPostContent.trim().isBlank()) {
            throw new PostIsEmptyException();
        }
        ParsedInfo parsedInfo = parseRawContent(rawPostContent);

        String slug = parsedInfo.getSlug();
        if (postRepository.existsBySlug(slug)) {
            throw new PostWithSlugAlreadyExistsException(slug);
        }
        var nowTimestamp = new TimestampSqlNow();
        var id = idGenerator.generate();
        var postToSave = Post.builder()
                .withId(id)
                .withTitle(parsedInfo.getTitle())
                .withSlug(slug)
                .withContentHtml(parsedInfo.outputData().orElseThrow())
                .withIsPublished(false)
                .withCreatedAt(nowTimestamp.now())
                .withUpdatedAt(nowTimestamp.now())
                .build();
        try {
            postRepository.save(postToSave);
            getLogger().info(">>> Post has been uploaded: {}", postToSave);
        } catch (Exception e) {
            getLogger().error(">>> Error uploading Post with slug '{}'", slug);
            throw new PostUploadException(postToSave, e);
        }

        return postToSave;
    }

    @Override
    public UUID uploadByReplace(String rawPostContent, String slug) {
        try {
            getLogger().info(">>> Replacing by slug: '{}'", slug);

            return dbManager.inTransaction(() -> {
                if (postRepository.existsBySlug(slug)) {
                    var id = postRepository.findBySlug(slug).orElseThrow().getId();
                    postRepository.deleteById(id);
                    getLogger().info(">>> Delete post '{}' with slug '{}'", id, slug);
                }
                Post uploaded = this.uploadPost(rawPostContent);
                getLogger().info(">>> New post version '{}' has been uploaded", slug);
                return uploaded.getId();
            });
        } catch (Exception e) {
            getLogger().error(">>> Error replacing post %s".formatted(slug), e);
            throw new PostReplaceBySlyException(slug, e);
        }
    }

    @Override
    public void publishPost(UUID postId) {
        getLogger().info(">>> Publishing post with ID: {}...", postId);
        try {
            dbManager.inTransaction(() -> {
                var post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

                post.setIs_published(true);
                var nowTimestamp = new TimestampSqlNow();
                post.setPublished_at(nowTimestamp.now());
                post.setUpdated_at(nowTimestamp.now());

                postRepository.update(post);
            });
        } catch (Exception e) {
            getLogger().error(">>> Error publishing Post with id '{}'", postId);
            throw new PostPublishException(postId, e);
        }
        getLogger().info(">>> Post with ID: {} has been published", postId);
    }

    @Override
    public void unpublishPost(UUID postId) {
        getLogger().info(">>> Unpublishing post with ID: {}...", postId);
        try {
            dbManager.inTransaction(() -> {
                var post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

                post.setIs_published(false);
                var nowTimestamp = new TimestampSqlNow();
                post.setPublished_at(null);
                post.setUpdated_at(nowTimestamp.now());

                postRepository.update(post);
            });
        } catch (Exception e) {
            getLogger().error(">>> Error unpublishing Post with id '{}'", postId);
            throw new PostUnpublishException(postId, e);
        }
        getLogger().info(">>> Post with ID: {} has been unpublished", postId);
    }

    @Override
    public Post getBySlug(String slug) {
        var post = postRepository.findBySlug(slug);

        return post.orElseThrow(() -> new PostNotFoundException("Post not fount by slug: %s".formatted(slug)));
    }

    @Override
    public Page<PostListItem> getPublishedPostListPaginated(int page, int pageSize) {
        var totalCount = postRepository.countPosts(true);
        var postList = postRepository.findPublishedPosts(page, pageSize);
        var totalPages = (int) Math.ceil((double) totalCount / pageSize);

        if (postList.isEmpty())
            throw new PostNotFoundException("No published posts found! [%s:%s]".formatted(page, pageSize));

        return new Page<>(
                postList,
                page,
                totalCount,
                totalPages,
                page < totalPages,
                page > 1
        );
    }

    @Override
    public List<PostListItem> getAllPostsItems() {

        return postRepository.findAllWithoutContent().stream()
                .map(post -> new PostListItem(
                        post.getId().toString(),
                        post.getTitle(),
                        post.isIs_published() ? post.getPublished_at().toLocalDateTime().toLocalDate() : null,
                        post.getSlug()
                ))
                .toList();
    }

    @Override
    public int deletePost(UUID postId) {
        getLogger().info(">>> Post with ID {} will be deleted", postId);

        return postRepository.deleteById(postId);
    }


    private ParsedInfo parseRawContent(String rawPostContent) {
        try {
            ParsedInfo parsedContent = textConverter.convert(rawPostContent);
            parsedContent.yamlDataMap().orElseThrow(PostHeaderIsEmptyException::new);
            parsedContent.outputData().orElseThrow(PostBodyIsEmptyException::new);
            getLogger().debug("Post has been parsed:\n Header: {}\nBody:[{}]",
                    parsedContent.yamlDataMap().orElse(Collections.emptyMap()),
                    parsedContent.outputData().orElse("EMPTY")
            );

            return parsedContent;
        } catch (Exception e) {
            String message = ">>> Error while parsing Post content! Raw data: %s".formatted(rawPostContent);
            getLogger().error(message);
            throw new PostParseException(message, e);
        }
    }

}
