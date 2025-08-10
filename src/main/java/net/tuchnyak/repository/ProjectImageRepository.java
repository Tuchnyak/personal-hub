package net.tuchnyak.repository;

import net.tuchnyak.model.portfolio.ProjectImage;
import net.tuchnyak.model.portfolio.ProjectImageIdOnly;

import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface ProjectImageRepository {

    List<ProjectImageIdOnly> getProjectImageIdListByProjectIdList(List<Integer> projectIdList);
    ProjectImage getImageById(int imageId);

}
