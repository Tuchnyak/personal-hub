package net.tuchnyak.element;

import net.tuchnyak.service.ProjectService;
import rife.engine.Context;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class ProjectElement extends AbstractLayoutElement {

    private final ProjectService projectService;

    public ProjectElement(ProjectService projectService) {
        super("projects_include");
        this.projectService = projectService;
    }

    public void process(Context c) {
        var projectTemplate = getLayoutTemplate(c);
        setCustomCss("css_projects");
        setTitle("George's - Projects");

        projectService.getAllProjectsWithImages()
                .forEach(projectDto -> {
                    projectTemplate.setValue("project_id", projectDto.getProject().getId());
                    projectTemplate.setValue("project_name", projectDto.getProject().getTitle());
                    projectTemplate.setValue("project_description", projectDto.getProject().getDescription());
                    projectTemplate.setValue("project_technologies", projectDto.getProject().getTechnologies());
                    projectTemplate.setValue("project_url", projectDto.getProject().getProject_url());
                    projectTemplate.setValue("project_repo_url", projectDto.getProject().getRepo_url());

                    var isFirstImage = new AtomicBoolean(true);
                    projectDto.getProjectImageIdList().forEach(imageId -> {
                        projectTemplate.setValue("image_id", imageId.getId());

                        // Populate preview buffer
                        projectTemplate.appendBlock("image_previews", "image_preview_item");

                        // Populate carousel buffers
                        if (isFirstImage.getAndSet(false)) {
                            projectTemplate.appendBlock("carousel_items", "carousel_item_active");
                        } else {
                            projectTemplate.appendBlock("carousel_items", "carousel_item");
                        }
                    });

                    projectTemplate.appendBlock("projects", "project");
                    // Clear the buffers for the next project
                    projectTemplate.removeValue("image_previews");
                    projectTemplate.removeValue("carousel_items");
                });

        c.print(projectTemplate);
    }

}
