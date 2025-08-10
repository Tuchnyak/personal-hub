package net.tuchnyak.router;

import net.tuchnyak.element.ProjectElement;
import net.tuchnyak.element.ProjectImageElement;
import net.tuchnyak.repository.ProjectImageRepositoryImpl;
import net.tuchnyak.repository.ProjectRepositoryImpl;
import net.tuchnyak.service.ProjectServiceImpl;
import net.tuchnyak.util.Logging;
import rife.database.Datasource;
import rife.engine.PathInfoHandling;
import rife.engine.Router;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class ProjectRouter extends Router implements Logging {

    private final Datasource dataSource;

    private ProjectServiceImpl projectService;

    public ProjectRouter(Datasource dataSource) {
        this.dataSource = dataSource;
    }

    public void setup() {
        projectService = new ProjectServiceImpl(
                new ProjectRepositoryImpl(dataSource),
                new ProjectImageRepositoryImpl(dataSource)
        );
        var rootRoute = get("/", PathInfoHandling.NONE, () -> new ProjectElement(projectService));
        get("", c -> c.redirect(rootRoute));

        get(
                "/image",
                PathInfoHandling.MAP(m -> m.p("image_id")),
                () -> new ProjectImageElement(projectService)
        );
        getLogger().info(">>> Project router setup");
    }

}
