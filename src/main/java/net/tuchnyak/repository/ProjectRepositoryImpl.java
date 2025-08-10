package net.tuchnyak.repository;

import net.tuchnyak.model.portfolio.Project;
import rife.database.Datasource;
import rife.database.DbQueryManager;
import rife.database.queries.Select;

import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class ProjectRepositoryImpl implements ProjectRepository {

    private final Datasource datasource;
    private final DbQueryManager dbQueryManager;

    public ProjectRepositoryImpl(Datasource datasource) {
        this.datasource = datasource;
        this.dbQueryManager = new DbQueryManager(datasource);
    }

    @Override
    public List<Project> getAllProjects() {
        var selectQuery = new Select(datasource)
                .from("portfolio.projects")
                .orderBy("sort_position");

        return dbQueryManager.executeFetchAllBeans(selectQuery, Project.class);
    }

}
