package net.tuchnyak.element;/**
 * @author tuchnyak (George Shchennikov)
 */

import net.tuchnyak.exception.post.PostNotFoundException;
import net.tuchnyak.service.PostUploadService;
import net.tuchnyak.util.Logging;
import rife.engine.*;

import static net.tuchnyak.service.PostUploadServiceImpl.ABOUT_SLUG;

public class IndexElement implements Element, Logging {

    private final PostUploadService postService;

    public IndexElement(PostUploadService postService) {
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

        var indexTemplate = c.template("layout");
        indexTemplate.setBlock("main_content", "index_include");
        indexTemplate.setValue("title", title);
        indexTemplate.setValue("content_body", body);

        c.print(indexTemplate);
    }

}