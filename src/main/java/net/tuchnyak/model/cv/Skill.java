package net.tuchnyak.model.cv;

public class Skill {
    private int id;
    private int sort_position;
    private String category;
    private String list;

    public Skill() {
    }

    public Skill(int id, int sort_position, String category, String list) {
        this.id = id;
        this.sort_position = sort_position;
        this.category = category;
        this.list = list;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", sort_position=" + sort_position +
                ", category='" + category + '\'' +
                ", list='" + list + '\'' +
                '}';
    }
}
