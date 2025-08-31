package net.tuchnyak.element;

import net.tuchnyak.exception.post.PostNotFoundException;
import net.tuchnyak.service.PostUploadService;
import net.tuchnyak.util.Logging;
import rife.engine.Context;
import rife.engine.Element;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class BlogPostElement implements Element, Logging {

    private final PostUploadService postService;

    public BlogPostElement(PostUploadService postService) {
        this.postService = postService;
    }

    @Override
    public void process(Context c) throws Exception {
        String slug = "/" + c.parameter("slug", "");

        var postTemplate = c.template("layout");
        postTemplate.setBlock("main_content", "blog_post_include");
        var title = "Post not found: " + slug;

        try {
            var post = postService.getBySlug(slug);
            title = post.getTitle();

            postTemplate.setBlock("page_content", "post_block");
            postTemplate.setValue("title", title);
            postTemplate.setValue("post_publish_date", post.getPublished_at().toLocalDateTime().toLocalDate());
            postTemplate.setValue("post_content", post.getContent_html());
        } catch (PostNotFoundException e) {
            getLogger().error(">>> Post not found: " + slug, e);
            postTemplate.setBlock("page_content", "empty_post_block");
        }

        c.print(postTemplate);
    }

}
