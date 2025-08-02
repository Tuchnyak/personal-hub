package net.tuchnyak.model;

public record ContactInfo(
        int id,
        int sortPosition,
        String property,
        String value) {
}
