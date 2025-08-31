package net.tuchnyak.dto;

import java.time.LocalDate;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostListItem {
    private String id;
    private String title;
    private LocalDate published_at;
    private String slug;

    public PostListItem() {
    }

    public PostListItem(String id, String title, LocalDate published_at, String slug) {
        this.id = id;
        this.title = title;
        this.published_at = published_at;
        this.slug = slug;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublished_at() {
        return published_at;
    }

    public void setPublished_at(LocalDate published_at) {
        this.published_at = published_at;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
