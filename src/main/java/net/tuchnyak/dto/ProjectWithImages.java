package net.tuchnyak.dto;

import net.tuchnyak.model.portfolio.Project;
import net.tuchnyak.model.portfolio.ProjectImage;

import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public record ProjectWithImages(
        Project project,
        List<ProjectImage> projectImageList
) {
}
