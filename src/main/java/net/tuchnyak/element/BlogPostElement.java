package net.tuchnyak.element;

import net.tuchnyak.exception.post.PostNotFoundException;
import net.tuchnyak.service.PostUploadService;
import rife.engine.Context;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class BlogPostElement extends AbstractLayoutElement {

    private final PostUploadService postService;
    private final boolean isDraftOk;

    public BlogPostElement(PostUploadService postService, boolean isDraftOk) {
        super("blog_post_include");
        this.postService = postService;
        this.isDraftOk = isDraftOk;
    }

    @Override
    public void process(Context c) throws Exception {
        String slug = "/" + c.parameter("slug", "");

        var postTemplate = activateLayoutTemplate(c);
        var title = "Post not found: " + slug;

        try {
            var post = postService.getBySlug(slug, isDraftOk);
            title = post.getTitle();
            setTitle(title);

            String publishDateToShow = post.getPublished_at() != null
                    ? post.getPublished_at().toLocalDateTime().toLocalDate().toString()
                    : "=== Not yet published ===";

            postTemplate.setBlock("page_content", "post_block");
            postTemplate.setValue("post_publish_date", publishDateToShow);
            postTemplate.setValue("post_content", post.getContent_html());
        } catch (PostNotFoundException e) {
            getLogger().error(">>> Post not found: " + slug, e);
            postTemplate.setBlock("page_content", "empty_post_block");
        }

        c.print(postTemplate);
    }

}
