package net.tuchnyak.model.cv;

import java.sql.Date;
public class WorkExperience {
    private int id;
    private String company_name;
    private String location;
    private String position;
    private String description;
    private String tech_list;
    private Date dat;
    private Date datto;

    public WorkExperience() {
    }

    public WorkExperience(int id, String company_name, String location, String position, String description, String tech_list, Date dat, Date datto) {
        this.id = id;
        this.company_name = company_name;
        this.location = location;
        this.position = position;
        this.description = description;
        this.tech_list = tech_list;
        this.dat = dat;
        this.datto = datto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTech_list() {
        return tech_list;
    }

    public void setTech_list(String tech_list) {
        this.tech_list = tech_list;
    }

    public Date getDat() {
        return dat;
    }

    public void setDat(Date dat) {
        this.dat = dat;
    }

    public Date getDatto() {
        return datto;
    }

    public void setDatto(Date datto) {
        this.datto = datto;
    }

    @Override
    public String toString() {
        return "WorkExperience{" +
                "id=" + id +
                ", company_name='" + company_name + '\'' +
                ", location='" + location + '\'' +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                ", tech_list='" + tech_list + '\'' +
                ", dat=" + dat +
                ", datto=" + datto +
                '}';
    }
}
