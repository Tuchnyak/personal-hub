package net.tuchnyak.element;

import net.tuchnyak.service.ProjectServiceImpl;
import net.tuchnyak.util.BlockAppendHandler;
import rife.engine.*;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class ProjectElement implements Element {

    private final ProjectServiceImpl projectService;

    public ProjectElement(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    public void process(Context c) {
        var projectTemplate = c.template("layout");
        projectTemplate.setBlock("main_content", "projects_include");
        projectTemplate.setBlock("custom_css", "css_projects");
        projectTemplate.setValue("title", "Georgii Shchennikov - Projects");

        projectService.getAllProjectsWithImages()
                .forEach(projectDto -> {
                    projectTemplate.setValue("project_name", projectDto.getProject().getTitle());
                    projectTemplate.setValue("project_description", projectDto.getProject().getDescription());
                    projectTemplate.setValue("project_technologies", projectDto.getProject().getTechnologies());
                    projectTemplate.setValue("project_url", projectDto.getProject().getProject_url());
                    projectTemplate.setValue("project_repo_url", projectDto.getProject().getRepo_url());
                    var blockAppender = new BlockAppendHandler(projectTemplate);
                    projectDto.getProjectImageIdList().forEach(imageId -> {
                        projectTemplate.setValue("image_id", imageId.getId());
                        blockAppender.setOrAppend("image_id_list");
                    });
                    projectTemplate.appendBlock("projects", "project");
                });

        c.print(projectTemplate);
    }

}