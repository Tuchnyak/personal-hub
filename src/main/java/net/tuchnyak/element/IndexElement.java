package net.tuchnyak.element;

import net.tuchnyak.exception.post.PostNotFoundException;
import net.tuchnyak.service.PostUploadService;
import rife.engine.*;

import static net.tuchnyak.service.PostUploadServiceImpl.ABOUT_SLUG;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class IndexElement extends AbstractLayoutElement {

    private final PostUploadService postService;

    public IndexElement(PostUploadService postService) {
        super("index_include");
        this.postService = postService;
    }

    public void process(Context c) throws Exception {
        String title;
        String body;
        try {
            var post = postService.getBySlug(ABOUT_SLUG);
            title = post.getTitle();
            body = post.getContent_html();
        } catch (PostNotFoundException e) {
            getLogger().warn("Problem during fetching About post data");
            title = "No title";
            body = "<p>Something gone wrong! Sorry!</p>";
        }

        var indexTemplate = activateLayoutTemplate(c);
        setTitle(title);
        indexTemplate.setValue("content_body", body);

        c.print(indexTemplate);
    }

}