package net.tuchnyak.model.portfolio;

import java.util.Arrays;

public class ProjectImage {
    private int id;
    private int project_id;
    private byte[] image_data;

    public ProjectImage() {
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

    public byte[] getImage_data() {
        return image_data;
    }

    public void setImage_data(byte[] image_data) {
        this.image_data = image_data;
    }

    @Override
    public String toString() {
        return "ProjectImage{" +
                "id=" + id +
                ", project_id=" + project_id +
                ", image_data=" + Arrays.toString(image_data).substring(0, 100).concat("...") +
                '}';
    }
}
