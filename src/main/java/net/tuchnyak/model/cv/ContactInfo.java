package net.tuchnyak.model.cv;

public class ContactInfo {
    private int id;
    private int sort_position;
    private String property;
    private String value;

    public ContactInfo() {
    }

    public ContactInfo(int id, int sort_position, String property, String value) {
        this.id = id;
        this.sort_position = sort_position;
        this.property = property;
        this.value = value;
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

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "id=" + id +
                ", sort_position=" + sort_position +
                ", property='" + property + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
