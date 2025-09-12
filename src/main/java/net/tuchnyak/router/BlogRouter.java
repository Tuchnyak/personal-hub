package net.tuchnyak.router;

import net.tuchnyak.element.BlogIndexElement;
import net.tuchnyak.element.BlogPostElement;
import net.tuchnyak.service.PostUploadService;
import net.tuchnyak.util.Logging;
import rife.engine.PathInfoHandling;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class BlogRouter extends AbstractRouter implements Logging {

    private final PostUploadService postService;

    public BlogRouter(PostUploadService postService) {
        super(new BlogIndexElement(postService));
        this.postService = postService;
    }

    @Override
    public void setup() {
        super.setup();
        get(
                "/page",
                PathInfoHandling.MAP(m -> m.p("page_number")),
                () -> new BlogIndexElement(postService)
        );
        get(
                "/post",
                PathInfoHandling.MAP(m -> m.p("slug")),
                () -> new BlogPostElement(postService)
        );

        getLogger().info(">>> Blog router setup");
    }
}
