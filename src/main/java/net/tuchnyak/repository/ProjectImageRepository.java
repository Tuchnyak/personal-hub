package net.tuchnyak.repository;

import net.tuchnyak.model.portfolio.ProjectImage;

import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface ProjectImageRepository {

    List<ProjectImage> getProjectImageListByProjectIdList(List<Integer> projectIdList);

}
