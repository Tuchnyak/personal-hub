package net.tuchnyak.service;

import net.tuchnyak.dto.ProjectWithImages;
import net.tuchnyak.model.portfolio.Project;
import net.tuchnyak.repository.ProjectImageRepository;
import net.tuchnyak.repository.ProjectRepository;

import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectImageRepository projectImageRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectImageRepository projectImageRepository) {
        this.projectRepository = projectRepository;
        this.projectImageRepository = projectImageRepository;
    }

    @Override
    public List<ProjectWithImages> getAllProjectsWithImages() {
        var projects = projectRepository.getAllProjects();
        var projectIdList = projects.stream().map(Project::getId).toList();
        var imageList = projectImageRepository.getProjectImageListByProjectIdList(projectIdList);

        return projects.stream()
                .map(project -> new ProjectWithImages(
                                project,
                                imageList.stream()
                                        .filter(image -> image.getProject_id() == project.getId())
                                        .toList()
                        )
                ).toList();
    }

}
