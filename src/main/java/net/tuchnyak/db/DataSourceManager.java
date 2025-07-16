package net.tuchnyak.db;

import rife.database.Datasource;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class DataSourceManager {

    private static final int POOL_SIZE = 5;

    private DataSourceManager() {
    }

    public Datasource getDataSource() {

        return DataSourceHolder.INSTANCE;
    }

    public static DataSourceManager getInstance() {

        return DataSourceManagerHolder.INSTANCE;
    }

    private static class DataSourceManagerHolder {
        private static final DataSourceManager INSTANCE = new DataSourceManager();
    }

    private static class DataSourceHolder {
        private static final Datasource INSTANCE = new Datasource(
                "org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:database/pershubdb;shutdown=true",
                "SA",
                "",
                POOL_SIZE
        );
    }
}
