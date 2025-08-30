package net.tuchnyak;

import net.tuchnyak.db.DataSourceManager;
import net.tuchnyak.db.DbInitializer;
import net.tuchnyak.db.MigrationImplementer;
import net.tuchnyak.element.IndexElement;
import net.tuchnyak.repository.CvRepository;
import net.tuchnyak.repository.CvRepositoryImpl;
import net.tuchnyak.repository.PostRepositoryImpl;
import net.tuchnyak.router.CvRouter;
import net.tuchnyak.router.ProjectRouter;
import net.tuchnyak.service.PostUploadService;
import net.tuchnyak.service.PostUploadServiceImpl;
import net.tuchnyak.util.Logging;
import rife.engine.*;

public class PersonalHubSite extends Site implements Logging {

    private final CvRepository cvRepository;
    private final PostUploadService postService;

    public PersonalHubSite() {
        new DbInitializer().initialize();
        new MigrationImplementer(
                DataSourceManager.getInstance().getDataSource(),
                new PostUploadServiceImpl(new PostRepositoryImpl(DataSourceManager.getInstance().getDataSource()))
        ).implementMigrations();
        cvRepository = new CvRepositoryImpl(DataSourceManager.getInstance().getDataSource());
        postService = new PostUploadServiceImpl(new PostRepositoryImpl(DataSourceManager.getInstance().getDataSource()));
    }

    public void setup() {
        get("/", new IndexElement(postService));

        var cvRouter = group("/cv", new CvRouter(cvRepository));
        group("/projects", new ProjectRouter(DataSourceManager.getInstance().getDataSource()));

        getLogger().info(">>> PersonalHubSite setup completed");
    }

    public static void main(String[] args) {
        new Server()
                .staticResourceBase("src/main/webapp")
                .start(new PersonalHubSite());
    }
}