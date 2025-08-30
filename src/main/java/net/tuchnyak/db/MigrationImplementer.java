package net.tuchnyak.db;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import net.tuchnyak.service.PostUploadService;
import net.tuchnyak.util.FileReaderUtil;
import net.tuchnyak.util.Logging;
import net.tuchnyak.util.ResourcesHandler;
import net.tuchnyak.util.ScriptRunner;
import rife.database.Datasource;
import rife.database.DbQueryManager;
import rife.database.queries.Select;

import static net.tuchnyak.service.PostUploadServiceImpl.ABOUT_SLUG;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class MigrationImplementer implements Logging {

    public static final String SRC_MIGRATIONS_DIR = "src/main/resources/db/migrations";

    private final Datasource dataSource;
    private final DbQueryManager queryManager;
    private final PostUploadService postUploadService;
    private final ResourcesHandler resourcesHandler;

    public MigrationImplementer(Datasource datasource, PostUploadService postUploadService) {
        this.dataSource = datasource;
        this.queryManager = new DbQueryManager(datasource);
        this.postUploadService = postUploadService;
        this.resourcesHandler = ResourcesHandler.getInstance();
    }

    public void implementMigrations() {
        Select selectDbVersion = new Select(dataSource)
                .field("version")
                .from("migration.db_version");
        AtomicInteger dbVersion = new AtomicInteger();
        queryManager.executeFetchFirst(selectDbVersion, rs -> {
            dbVersion.set(rs.getInt("version"));
        });
        getLogger().info(">>> Current db version: {}", dbVersion.get());

        var dir = new File(SRC_MIGRATIONS_DIR);
        File[] migrationFiles = dir.listFiles(file -> file.getName().startsWith("v")
                && file.getName().endsWith(".sql"));
        if (migrationFiles == null || migrationFiles.length == 0)
            return;

        DbQueryManager queryManager = new DbQueryManager(dataSource);
        ScriptRunner scriptRunner = new ScriptRunner(queryManager);
        FileReaderUtil fileReaderUtil = new FileReaderUtil();
        queryManager.inTransaction(() -> {
            Arrays.stream(migrationFiles)
                    .filter(f -> {
                        var strVersion = f.getName().split("\\.")[0].substring(1);
                        var version = Integer.parseInt(strVersion);
                        return version > dbVersion.get();
                    })
                    .sorted(Comparator.comparing(File::getName))
                    .forEach(file -> {
                        getLogger().info(">>> Applying migration: {}", file.getName());
                        try {
                            var migrationScript = fileReaderUtil.readFilePathToString(file.toPath());
                            scriptRunner.executeUpdate(migrationScript);
                            getLogger().info(">>> Migration script applied: {}", file.getName());
                        } catch (Exception e) {
                            getLogger().error(">>> Error applying migration script: {}", e.getMessage());
                            throw new RuntimeException(e);
                        }
                    });
        });
        getLogger().info(">>> Migration completed");

        postMigration();
    }

    private void postMigration() {
        getLogger().info(">>> Postmigration started");
        var aboutMdContent = resourcesHandler.getFileContent("md/about.md");
        aboutMdContent.ifPresent(rawPost -> {
            var uuid = postUploadService.uploadByReplace(rawPost, ABOUT_SLUG);
            if (uuid == null) {
                getLogger().warn(">>> About me post not uploaded!");
            } else {
                getLogger().info(">>> About me post uploaded: {}", uuid);
                postUploadService.publishPost(uuid);
            }
        });
        getLogger().info(">>> Postmigration completed");
    }

}
