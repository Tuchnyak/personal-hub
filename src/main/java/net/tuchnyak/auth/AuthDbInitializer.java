package net.tuchnyak.auth;

import net.tuchnyak.config.AppConfigurations;
import net.tuchnyak.util.Logging;
import rife.authentication.credentialsmanagers.DatabaseUsers;
import rife.database.Datasource;
import rife.database.queries.Select;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class AuthDbInitializer implements Logging {

    private final Datasource dataSource;
    private final AppConfigurations appConfigurations;
    private final DatabaseUsers dbUsers;

    public AuthDbInitializer(Datasource dataSource, AppConfigurations appConfigurations, DatabaseUsers dbUsers) {
        this.dataSource = dataSource;
        this.appConfigurations = appConfigurations;
        this.dbUsers = dbUsers;
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
            });
            getLogger().info(">>> Auth DB structure initialization completed");
        }
    }

}
