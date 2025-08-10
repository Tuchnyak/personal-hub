package net.tuchnyak.repository;

import net.tuchnyak.model.portfolio.Project;

import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface ProjectRepository {

    List<Project> getAllProjects();

}
