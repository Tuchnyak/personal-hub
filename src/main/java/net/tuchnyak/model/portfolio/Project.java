package net.tuchnyak.model.portfolio;

public class Project {
    private int id;
    private int sort_position;
    private String title;
    private String description;
    private String technologies;
    private String project_url;
    private String repo_url;

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSort_position() {
        return sort_position;
    }

    public void setSort_position(int sort_position) {
        this.sort_position = sort_position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getProject_url() {
        return project_url;
    }

    public void setProject_url(String project_url) {
        this.project_url = project_url;
    }

    public String getRepo_url() {
        return repo_url;
    }

    public void setRepo_url(String repo_url) {
        this.repo_url = repo_url;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", sort_position=" + sort_position +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", technologies='" + technologies + '\'' +
                ", project_url='" + project_url + '\'' +
                ", repo_url='" + repo_url + '\'' +
                '}';
    }
}
