package net.tuchnyak.element;

import net.tuchnyak.exception.post.PostNotFoundException;
import net.tuchnyak.service.PostUploadService;
import rife.engine.Context;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class BlogPostElement extends AbstractLayoutElement {

    private final PostUploadService postService;

    public BlogPostElement(PostUploadService postService) {
        super("blog_post_include");
        this.postService = postService;
    }

    @Override
    public void process(Context c) throws Exception {
        String slug = "/" + c.parameter("slug", "");

        var postTemplate = activateLayoutTemplate(c);
        var title = "Post not found: " + slug;

        try {
            var post = postService.getBySlug(slug);
            title = post.getTitle();
            setTitle(title);

            postTemplate.setBlock("page_content", "post_block");
            postTemplate.setValue("post_publish_date", post.getPublished_at().toLocalDateTime().toLocalDate());
            postTemplate.setValue("post_content", post.getContent_html());
        } catch (PostNotFoundException e) {
            getLogger().error(">>> Post not found: " + slug, e);
            postTemplate.setBlock("page_content", "empty_post_block");
        }

        c.print(postTemplate);
    }

}
