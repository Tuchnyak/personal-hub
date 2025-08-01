package net.tuchnyak.model;

public record WorkExperience(
        int id,
        String company_name,
        String location,
        String position,
        String description,
        String tech_list,
        java.sql.Date dat,
        java.sql.Date datto) {
}
