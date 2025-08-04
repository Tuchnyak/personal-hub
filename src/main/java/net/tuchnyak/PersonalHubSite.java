package net.tuchnyak;

import net.tuchnyak.db.DataSourceManager;
import net.tuchnyak.db.DbInitializer;
import net.tuchnyak.db.MigrationImplementer;
import net.tuchnyak.repository.CvRepository;
import net.tuchnyak.repository.CvRepositoryImpl;
import net.tuchnyak.router.CvRouter;
import net.tuchnyak.util.Logging;
import rife.engine.*;

public class PersonalHubSite extends Site implements Logging {

    private final CvRepository cvRepository;

    public PersonalHubSite() {
        new DbInitializer().initialize();
        new MigrationImplementer(DataSourceManager.getInstance().getDataSource()).implementMigrations();
        cvRepository = new CvRepositoryImpl(DataSourceManager.getInstance().getDataSource());
    }

    public void setup() {
        var cvRouter = group("/cv", new CvRouter(cvRepository));
        get("/", c -> c.redirect(cvRouter.getRootPath()));

        getLogger().info(">>> PersonalHubSite setup completed");
    }

    public static void main(String[] args) {
        new Server()
                .staticResourceBase("src/main/webapp")
                .start(new PersonalHubSite());
    }
}