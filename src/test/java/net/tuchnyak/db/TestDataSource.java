package net.tuchnyak.db;

import rife.database.Datasource;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class TestDataSource {

    private TestDataSource() {}

    public static Datasource getDataSource() {

        return DataSourceHolder.INSTANCE;
    }

    private static class DataSourceHolder {
        private static final Datasource INSTANCE = new Datasource(
                "org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:mem:pershubdb;shutdown=true",
                "SA",
                "",
                1
        );
    }

}
