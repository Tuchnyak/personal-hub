package net.tuchnyak.repository;

import net.tuchnyak.model.portfolio.ProjectImage;
import net.tuchnyak.model.portfolio.ProjectImageIdOnly;
import rife.database.Datasource;
import rife.database.DbQueryManager;
import rife.database.queries.Select;

import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class ProjectImageRepositoryImpl implements ProjectImageRepository {

    private final Datasource datasource;
    private final DbQueryManager dbQueryManager;

    public ProjectImageRepositoryImpl(Datasource datasource) {
        this.datasource = datasource;
        this.dbQueryManager = new DbQueryManager(datasource);
    }

    @Override
    public List<ProjectImageIdOnly> getProjectImageIdListByProjectIdList(List<Integer> projectIdList) {
        var select = new Select(datasource)
                .fields("id", "project_id")
                .from("portfolio.project_images")
                .where("project_id", "in", projectIdList);

        return dbQueryManager.executeFetchAllBeans(select, ProjectImageIdOnly.class);
    }

    @Override
    public ProjectImage getImageById(int imageId) {
        var select = new Select(datasource)
                .from("portfolio.project_images")
                .where("id", "=", imageId);

        return dbQueryManager.executeFetchFirstBean(select, ProjectImage.class);
    }

}
