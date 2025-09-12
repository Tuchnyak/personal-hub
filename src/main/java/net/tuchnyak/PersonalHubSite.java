package net.tuchnyak;

import net.tuchnyak.auth.AuthDbInitializer;
import net.tuchnyak.auth.CredsHolder;
import net.tuchnyak.config.AppConfigurations;
import net.tuchnyak.db.DataSourceManager;
import net.tuchnyak.db.DbInitializer;
import net.tuchnyak.db.MigrationImplementer;
import net.tuchnyak.element.IndexElement;
import net.tuchnyak.repository.*;
import net.tuchnyak.router.AdminRouter;
import net.tuchnyak.router.BlogRouter;
import net.tuchnyak.router.CvRouter;
import net.tuchnyak.router.ProjectRouter;
import net.tuchnyak.service.PostUploadService;
import net.tuchnyak.service.PostUploadServiceImpl;
import net.tuchnyak.service.ProjectService;
import net.tuchnyak.service.ProjectServiceImpl;
import net.tuchnyak.util.Logging;
import rife.authentication.credentialsmanagers.DatabaseUsers;
import rife.authentication.credentialsmanagers.DatabaseUsersFactory;
import rife.authentication.elements.AuthConfig;
import rife.authentication.elements.Login;
import rife.authentication.elements.Logout;
import rife.authentication.sessionmanagers.DatabaseSessions;
import rife.authentication.sessionmanagers.DatabaseSessionsFactory;
import rife.authentication.sessionvalidators.DatabaseSessionValidator;
import rife.authentication.sessionvalidators.DatabaseSessionValidatorFactory;
import rife.config.RifeConfig;
import rife.database.Datasource;
import rife.engine.Server;
import rife.engine.Site;
import rife.ioc.HierarchicalProperties;
import rife.template.TemplateFactory;
import rife.tools.StringEncryptor;

import java.util.Optional;

import static net.tuchnyak.auth.AuthDbInitializer.ADMIN;

public class PersonalHubSite extends Site implements Logging {

    public static final StringEncryptor DRUPAL_PASSWORD_ENCRYPTOR = StringEncryptor.DRUPAL;

    private final CvRepository cvRepository;
    private final ProjectService projectService;
    private final PostUploadService postService;
    private final CredsHolder credsHolder;

    private AppConfigurations appConfigurations;
    private DatabaseUsers dbUsers;
    private DatabaseSessions databaseSessions;
    private AuthConfig authConfig;

    public PersonalHubSite(Optional<String> newCredentials) {
        new DbInitializer().initialize();
        var dataSource = DataSourceManager.getInstance().getDataSource();
        new MigrationImplementer(
                dataSource,
                new PostUploadServiceImpl(new PostRepositoryImpl(dataSource))
        ).implementMigrations();
        cvRepository = new CvRepositoryImpl(dataSource);
        postService = new PostUploadServiceImpl(new PostRepositoryImpl(dataSource));
        projectService = new ProjectServiceImpl(
                new ProjectRepositoryImpl(dataSource),
                new ProjectImageRepositoryImpl(dataSource)
        );
        credsHolder = new CredsHolder(newCredentials);
        getLogger().info(">>> PersonalHubSite initialized");
    }

    public void setup() {
        setupConfig();

        get("/", new IndexElement(postService));

        group("/cv", new CvRouter(cvRepository));
        group("/projects", new ProjectRouter(projectService));
        group("/blog", new BlogRouter(postService));

        var login = getPost(appConfigurations.authProperties().endpointLogin(), new Login(authConfig, TemplateFactory.HTML.get("auth/login")));
        get(appConfigurations.authProperties().endpointLogout(), new Logout(authConfig, TemplateFactory.HTML.get("auth/logout")));
        AdminRouter adminRouter = new AdminRouter(appConfigurations, authConfig);
        group(appConfigurations.authProperties().endpointAdmin(), adminRouter);
        authConfig
                .loginRoute(login)
                .landingRoute(adminRouter.getRootRoute());

        getLogger().info(">>> PersonalHubSite setup completed");
    }


    private void setupConfig() {
        setupConfiguration(properties());
        setupAuthDb(DataSourceManager.getInstance().getDataSource(), appConfigurations, credsHolder);
        setWebAuthConfiguration();
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

    private void setupAuthDb(Datasource dataSource, AppConfigurations appConfigurations, CredsHolder credsHolder) {
        dbUsers = DatabaseUsersFactory.instance(dataSource);
        dbUsers.setPasswordEncryptor(DRUPAL_PASSWORD_ENCRYPTOR);

        databaseSessions = DatabaseSessionsFactory.instance(DataSourceManager.getInstance().getDataSource());
        databaseSessions.setSessionDuration(appConfigurations.authProperties().durationMinutes() * 60 * 1000L);

        var aDbI = new AuthDbInitializer(dataSource, appConfigurations, dbUsers, databaseSessions);
        aDbI.init();
        aDbI.addUser(credsHolder);
    }

    private void setWebAuthConfiguration() {
        DatabaseSessionValidator dbValidator = DatabaseSessionValidatorFactory.instance(DataSourceManager.getInstance().getDataSource());
        dbValidator.setCredentialsManager(dbUsers);
        dbValidator.setSessionManager(databaseSessions);

        authConfig = new AuthConfig(dbValidator);
        authConfig.role(ADMIN);
        authConfig.allowRemember(false);
    }


    /////////////////////////////////
    public static void main(String[] args) {
        Optional<String> creds;
        if (args.length == 2) {
            creds = Optional.of(args[0] + ":" + args[1]);
        } else {
            creds = Optional.empty();
        }
        new Server()
                .staticResourceBase("src/main/webapp")
                .start(new PersonalHubSite(creds));
    }
}