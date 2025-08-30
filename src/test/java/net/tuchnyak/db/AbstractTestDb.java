package net.tuchnyak.db;

import net.tuchnyak.util.FileReaderUtil;
import net.tuchnyak.util.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import rife.database.Datasource;
import rife.database.DbQueryManager;
import rife.resources.ResourceFinderDirectories;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author tuchnyak (George Shchennikov)
 */
public abstract class AbstractTestDb {

    protected static Datasource testDataSource;
    protected static DbQueryManager queryManager;
    protected static ResourceFinderDirectories resourceFinder;

    @BeforeAll
    protected static void beforeAllAbstract() throws Exception {
        initDb();
        queryManager = new DbQueryManager(testDataSource);
    }

    @AfterAll
    protected static void afterAllAbstract() {
        testDataSource.close();
        testDataSource = null;
        queryManager = null;
    }

    protected static void initDb() throws Exception {
        testDataSource = TestDataSource.getDataSource();
        resourceFinder = new ResourceFinderDirectories(
                new File("src/test/resources/db"),
                new File("src/main/resources")
        );
        new ScriptRunner(new DbQueryManager(testDataSource))
                .executeUpdate(
                        new FileReaderUtil().readFilePathToString(
                                Paths.get(resourceFinder.getResource("test_init.sql").toURI())
                        )
                );
    }

}
