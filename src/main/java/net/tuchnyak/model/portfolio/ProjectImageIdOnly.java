package net.tuchnyak.model.portfolio;

public class ProjectImageIdOnly {
    private int id;
    private int project_id;

    public ProjectImageIdOnly() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    @Override
    public String toString() {
        return "ProjectImage{" +
                "id=" + id +
                ", project_id=" + project_id +
                '}';
    }
}
