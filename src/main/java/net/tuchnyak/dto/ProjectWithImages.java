package net.tuchnyak.dto;

import net.tuchnyak.model.portfolio.Project;
import net.tuchnyak.model.portfolio.ProjectImageIdOnly;

import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class ProjectWithImages {
    private String test = "test";
    private Project project;
    private List<ProjectImageIdOnly> projectImageIdList;

    public ProjectWithImages() {
    }

    public ProjectWithImages(Project project, List<ProjectImageIdOnly> projectImageIdList) {
        this.project = project;
        this.projectImageIdList = projectImageIdList;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ProjectImageIdOnly> getProjectImageIdList() {
        return projectImageIdList;
    }

    public void setProjectImageIdList(List<ProjectImageIdOnly> projectImageIdList) {
        this.projectImageIdList = projectImageIdList;
    }

    @Override
    public String toString() {
        return "ProjectWithImages{" +
                "project=" + project +
                ", projectImageIdList=" + projectImageIdList +
                '}';
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}