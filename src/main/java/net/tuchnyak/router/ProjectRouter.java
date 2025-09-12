package net.tuchnyak.router;

import net.tuchnyak.element.ProjectElement;
import net.tuchnyak.element.ProjectImageElement;
import net.tuchnyak.service.ProjectService;
import net.tuchnyak.util.Logging;
import rife.engine.PathInfoHandling;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class ProjectRouter extends AbstractRouter implements Logging {

    private ProjectService projectService;

    public ProjectRouter(ProjectService projectService) {
        super(new ProjectElement(projectService));
        this.projectService = projectService;
    }

    @Override
    public void setup() {
        super.setup();
        get(
                "/image",
                PathInfoHandling.MAP(m -> m.p("image_id")),
                () -> new ProjectImageElement(projectService)
        );
        getLogger().info(">>> Project router setup");
    }

}
