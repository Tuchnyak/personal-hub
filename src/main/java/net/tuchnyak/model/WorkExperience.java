package net.tuchnyak.model;

public record WorkExperience(
        int id,
        String companyName,
        String location,
        String position,
        String description,
        String techList,
        java.sql.Date dat,
        java.sql.Date datto) {
}
