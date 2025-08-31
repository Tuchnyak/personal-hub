package net.tuchnyak.router;

import net.tuchnyak.element.BlogIndexElement;
import net.tuchnyak.service.PostUploadService;
import net.tuchnyak.util.Logging;
import rife.engine.PathInfoHandling;
import rife.engine.Router;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class BlogRouter extends Router implements Logging {

    private final PostUploadService postService;

    public BlogRouter(PostUploadService postService) {
        this.postService = postService;
    }

    @Override
    public void setup() {
        var rootBlogRoute = get(
                "/",
                PathInfoHandling.NONE,
                () -> new BlogIndexElement(postService)
        );
        get("", c -> c.redirect(rootBlogRoute));
        get(
                "/page",
                PathInfoHandling.MAP(m -> m.p("page_number")),
                () -> new BlogIndexElement(postService)
        );
//        get(
//                "/post",
//                PathInfoHandling.MAP(m -> m.p("slug")),
//                () -> new BlogPostElement(postService)
//        );

        getLogger().info(">>> Blog router setup");
    }
}
