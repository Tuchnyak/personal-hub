package net.tuchnyak.repository;

import net.tuchnyak.dto.Page;
import net.tuchnyak.dto.PostListItem;
import net.tuchnyak.model.blog.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface PostRepository {

    void save(Post post);

    void update(Post post);

    boolean existsBySlug(String slug);

    int delete(Post post);

    int deleteById(UUID id);

    List<Post> findAll();

    List<Post> findAllWithoutContent();

    Optional<Post> findBySlug(String slug);

    Optional<Post> findById(UUID slug);

    List<PostListItem> findPublishedPosts(int page, int pageSize);

    int countPosts(boolean publishedOnly);

}
