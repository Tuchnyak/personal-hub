package net.tuchnyak.element;/**
 * @author tuchnyak (George Shchennikov)
 */

import net.tuchnyak.service.ProjectServiceImpl;
import rife.engine.*;

public class ProjectElement implements Element {

    private final ProjectServiceImpl projectService;

    public ProjectElement(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    public void process(Context c) throws Exception {
        var projectTemplate = c.template("projects");

        projectService.getAllProjectsWithImages()
                .forEach(projectDto -> {
                    projectTemplate.setValue("project_name", projectDto.project().getTitle());
                    projectTemplate.setValue("project_description", projectDto.project().getDescription());
                    projectTemplate.setValue("project_technologies", projectDto.project().getTechnologies());
                    projectTemplate.setValue("project_url", projectDto.project().getProject_url());
                    projectTemplate.setValue("project_repo_url", projectDto.project().getRepo_url());
                    projectDto.projectImageIdList().forEach(imageId -> {
                        projectTemplate.setValue("image_id", imageId.getId());
                        projectTemplate.appendBlock("image_id_list");
                    });
                    projectTemplate.appendBlock("projects");
                    projectTemplate.removeValue("image_id_list");
                });

        c.print(projectTemplate);
    }

}