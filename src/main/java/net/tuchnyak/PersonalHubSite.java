package net.tuchnyak;

import net.tuchnyak.auth.AuthDbInitializer;
import net.tuchnyak.config.AppConfigurations;
import net.tuchnyak.db.DataSourceManager;
import net.tuchnyak.db.DbInitializer;
import net.tuchnyak.db.MigrationImplementer;
import net.tuchnyak.element.IndexElement;
import net.tuchnyak.repository.CvRepository;
import net.tuchnyak.repository.CvRepositoryImpl;
import net.tuchnyak.repository.PostRepositoryImpl;
import net.tuchnyak.router.BlogRouter;
import net.tuchnyak.router.CvRouter;
import net.tuchnyak.router.ProjectRouter;
import net.tuchnyak.service.PostUploadService;
import net.tuchnyak.service.PostUploadServiceImpl;
import net.tuchnyak.util.Logging;
import rife.authentication.credentialsmanagers.DatabaseUsersFactory;
import rife.config.RifeConfig;
import rife.database.Datasource;
import rife.engine.*;
import rife.ioc.HierarchicalProperties;

public class PersonalHubSite extends Site implements Logging {

    private final CvRepository cvRepository;
    private final PostUploadService postService;

    private AppConfigurations appConfigurations;

    public PersonalHubSite() {
        new DbInitializer().initialize();
        var dataSource = DataSourceManager.getInstance().getDataSource();
        new MigrationImplementer(
                dataSource,
                new PostUploadServiceImpl(new PostRepositoryImpl(dataSource))
        ).implementMigrations();
        cvRepository = new CvRepositoryImpl(dataSource);
        postService = new PostUploadServiceImpl(new PostRepositoryImpl(dataSource));
        getLogger().info(">>> PersonalHubSite initialized");
    }

    public void setup() {
        setupConfiguration(properties());
        setupAuthDb(DataSourceManager.getInstance().getDataSource(), appConfigurations);

        get("/", new IndexElement(postService));

        group("/cv", new CvRouter(cvRepository));
        group("/projects", new ProjectRouter(DataSourceManager.getInstance().getDataSource()));
        group("/blog", new BlogRouter(postService));

        getLogger().info(">>> PersonalHubSite setup completed");
    }


    private void setupConfiguration(HierarchicalProperties properties) {
        appConfigurations = new AppConfigurations(properties);

        var rifeAuthProps = RifeConfig.authentication();
        rifeAuthProps.setSequenceRole(appConfigurations.authProperties().sequenceAuthRole());
        rifeAuthProps.setTableUser(appConfigurations.authProperties().tableUser());
        rifeAuthProps.setTableRole(appConfigurations.authProperties().tableRole());
        rifeAuthProps.setTableRoleLink(appConfigurations.authProperties().tableUserRole());
        rifeAuthProps.setTableAuthentication(appConfigurations.authProperties().tableAuthentication());
        rifeAuthProps.setTableRemember(appConfigurations.authProperties().tableRemember());
    }

    private void setupAuthDb(Datasource dataSource, AppConfigurations appConfigurations) {
        var dbUsers = DatabaseUsersFactory.instance(dataSource);
        var aDbI = new AuthDbInitializer(dataSource, appConfigurations, dbUsers);
        aDbI.init();
    }


    /////////////////////////////////
    public static void main(String[] args) {
        new Server()
                .staticResourceBase("src/main/webapp")
                .start(new PersonalHubSite());
    }
}