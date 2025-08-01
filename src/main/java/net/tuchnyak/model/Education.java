package net.tuchnyak.model;

public record Education(
        int id,
        String institution_name,
        String degree,
        String description,
        String graduation_year) {
}
