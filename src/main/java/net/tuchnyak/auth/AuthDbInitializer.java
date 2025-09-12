package net.tuchnyak.auth;

import net.tuchnyak.config.AppConfigurations;
import net.tuchnyak.util.Logging;
import rife.authentication.credentialsmanagers.DatabaseUsers;
import rife.authentication.credentialsmanagers.RoleUserAttributes;
import rife.authentication.sessionmanagers.DatabaseSessions;
import rife.database.Datasource;
import rife.database.queries.Select;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class AuthDbInitializer implements Logging {

    public static final String ADMIN = "admin";

    private final Datasource dataSource;
    private final AppConfigurations appConfigurations;
    private final DatabaseUsers dbUsers;
    private final DatabaseSessions databaseSessions;
    private final EncryptionService encryptionService;

    public AuthDbInitializer(Datasource dataSource, AppConfigurations appConfigurations, DatabaseUsers dbUsers, DatabaseSessions databaseSessions) {
        this.dataSource = dataSource;
        this.appConfigurations = appConfigurations;
        this.dbUsers = dbUsers;
        this.databaseSessions = databaseSessions;
        this.encryptionService = new EncryptionServiceDrupalImpl();
    }

    public void init() {
        boolean isAuthAlreadyInitialized;
        try {
            Integer count = dbUsers.executeGetFirstInt(new Select(dataSource)
                    .field("count(*)")
                    .from(appConfigurations.authProperties().tableUser())
            );
            getLogger().info(">>> Auth tables already initialized. Count: {}", count);
            isAuthAlreadyInitialized = true;
        } catch (Exception e) {
            getLogger().warn(">>> Auth tables not found. Creating auth tables...", e);
            isAuthAlreadyInitialized = false;
        }

        try {
            initNewAuthStructure(isAuthAlreadyInitialized);
        } catch (Exception e) {
            getLogger().error(">>> Auth DB structure initialization failed", e);
        }

        addAdminRole();
    }

    private void initNewAuthStructure(boolean isAuthAlreadyInitialized) {
        if (isAuthAlreadyInitialized) {
            getLogger().info(">>> Auth DB structure already initialized");
        } else {
            getLogger().info(">>> Auth DB structure initialization started");
            dbUsers.inTransaction(() -> {
                dbUsers.executeUpdate("create schema %s authorization dba"
                        .formatted(appConfigurations.authProperties().schema()));
                // create tables: user, role, user_role
                dbUsers.install();
                getLogger().info(">>> Auth tables created");
                databaseSessions.install();
                getLogger().info(">>> Auth sessions tables created");
            });
            getLogger().info(">>> Auth DB structure initialization completed");
        }
    }

    private void addAdminRole() {
        try {
            if (!dbUsers.containsRole(ADMIN)) {
                dbUsers.addRole(ADMIN);
            }
        } catch (Exception e) {
            getLogger().error(">>> Admin role initialization failed", e);
        }
    }

    public void addUser(CredsHolder credsHolder) {
        credsHolder.ifPresent(creds -> {
            try {
                if (!dbUsers.containsUser(creds.getUserName())) {
                    getLogger().info(">>> Saveing user {}", creds.getUserName());
                    dbUsers.addUser(creds.getUserName(), new RoleUserAttributes(creds.getPassword()).role(ADMIN));
                } else {
                    if (!encryptionService.checkPassword(creds.getPassword(), dbUsers.getPassword(creds.getUserName()))) {
                        getLogger().info(">>> Update user {}", creds.getUserName());
                        dbUsers.updateUser(creds.getUserName(), new RoleUserAttributes(creds.getPassword()).role(ADMIN));
                    }
                }
            } catch (Exception e) {
                getLogger().error(">>> Error during adding user: " + creds.getUserName(), e);
            } finally {
                creds.dropCreds();
            }
        });
    }

}
