package net.tuchnyak.model;

public class Education {
    private int id;
    private String institution_name;
    private String degree;
    private String description;
    private String graduation_year;

    public Education() {
    }

    public Education(int id, String institution_name, String degree, String description, String graduation_year) {
        this.id = id;
        this.institution_name = institution_name;
        this.degree = degree;
        this.description = description;
        this.graduation_year = graduation_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstitution_name() {
        return institution_name;
    }

    public void setInstitution_name(String institution_name) {
        this.institution_name = institution_name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGraduation_year() {
        return graduation_year;
    }

    public void setGraduation_year(String graduation_year) {
        this.graduation_year = graduation_year;
    }

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", institution_name='" + institution_name + '\'' +
                ", degree='" + degree + '\'' +
                ", description='" + description + '\'' +
                ", graduation_year='" + graduation_year + '\'' +
                '}';
    }
}
