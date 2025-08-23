package net.tuchnyak.db;

import net.tuchnyak.util.FileReaderUtil;
import net.tuchnyak.util.Logging;
import net.tuchnyak.util.ScriptRunner;
import rife.database.Datasource;
import rife.database.DbQueryManager;
import rife.resources.ResourceFinder;
import rife.resources.ResourceFinderDirectories;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class DbInitializer implements Logging {

    public static final String DB_DIR_RELATIVE_PATH = "./database";
    public static final String DB_NAME = "pershubdb";
    public static final String SRC_MAIN_RESOURCES_DB = "src/main/resources/db";
    public static final String SCHEMA_SQL = "schema.sql";

    public void initialize() {
        var dbDirFile = new File(DB_DIR_RELATIVE_PATH);
        if (!dbDirFile.exists()) {
            getLogger().info(">>> Creating database directory: {}", dbDirFile.mkdir());
        } else {
            getLogger().info(">>> Database directory already exists: {}", dbDirFile.getAbsolutePath());
        }

        File[] array = dbDirFile.listFiles(File::isFile);
        if (array == null) {
            getLogger().info(">>> Database directory is empty: {}", dbDirFile.getAbsolutePath());
            return;
        }

        var isDbExists = Arrays.stream(array).anyMatch(file -> file.getName().contains(DB_NAME));
        if (!isDbExists) {
            getLogger().info(">>> Database files not found in directory: {}\n>>> Creating new database...",
                    dbDirFile.getAbsolutePath());
            Datasource dataSource = DataSourceManager.getInstance().getDataSource();
            try {
                ResourceFinder resourceFinder = new ResourceFinderDirectories(new File(SRC_MAIN_RESOURCES_DB));
                URL resource = resourceFinder.getResource(SCHEMA_SQL);
                var sqlScript = new FileReaderUtil().readFilePathToString(Paths.get(resource.toURI()));
                DbQueryManager queryManager = new DbQueryManager(dataSource);
                new ScriptRunner(queryManager).executeUpdate(sqlScript);
                getLogger().info(">>> Database created successfully.");
            } catch (Exception e) {
                getLogger().error(">>> Error creating database: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            getLogger().info(">>> Database files already exist in directory: {}", dbDirFile.getAbsolutePath());
        }
    }

}
