package net.tuchnyak.model;

public record ContactInfo(
        int id,
        int sort_position,
        String property,
        String value) {
}
