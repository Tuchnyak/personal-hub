package net.tuchnyak.service;

import net.tuchnyak.dto.ProjectWithImages;
import net.tuchnyak.model.portfolio.ProjectImage;

import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface ProjectService {

    List<ProjectWithImages> getAllProjectsWithImages();
    ProjectImage getProjectImageById(int imageId);

}
