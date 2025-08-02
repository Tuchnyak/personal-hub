package net.tuchnyak.model;

public record Education(
        int id,
        String institutionName,
        String degree,
        String description,
        String graduationYear) {
}
