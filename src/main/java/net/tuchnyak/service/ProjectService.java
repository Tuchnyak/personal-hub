package net.tuchnyak.service;

import net.tuchnyak.dto.ProjectWithImages;

import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface ProjectService {

    List<ProjectWithImages> getAllProjectsWithImages();

}
