package net.tuchnyak.model.blog;

import net.tuchnyak.util.StringUtil;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class Post {

    private UUID id;
    private String title;
    private String slug;
    private String content_html;
    private boolean is_published;
    private Timestamp published_at;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Post() {
    }

    private Post(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.slug = builder.slug;
        this.content_html = builder.content_html;
        this.is_published = builder.is_published;
        this.published_at = builder.published_at;
        this.created_at = builder.created_at;
        this.updated_at = builder.updated_at;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent_html() {
        return content_html;
    }

    public void setContent_html(String content_html) {
        this.content_html = content_html;
    }

    public boolean isIs_published() {
        return is_published;
    }

    public void setIs_published(boolean is_published) {
        this.is_published = is_published;
    }

    public Timestamp getPublished_at() {
        return published_at;
    }

    public void setPublished_at(Timestamp published_at) {
        this.published_at = published_at;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", contentHtml='" + new StringUtil().cutIfTooLong(content_html, 100) + '\'' +
                ", isPublished=" + is_published +
                ", publishedAt=" + published_at +
                ", createdAt=" + created_at +
                ", updatedAt=" + updated_at +
                '}';
    }

    public static class Builder {
        private UUID id;
        private String title;
        private String slug;
        private String content_html;
        private boolean is_published = false;
        private Timestamp published_at;
        private Timestamp created_at;
        private Timestamp updated_at;

        public Builder withId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withSlug(String slug) {
            this.slug = slug;
            return this;
        }

        public Builder withContentHtml(String content_html) {
            this.content_html = content_html;
            return this;
        }

        public Builder withIsPublished(boolean is_published) {
            this.is_published = is_published;
            return this;
        }

        public Builder withPublishedAt(Timestamp published_at) {
            this.published_at = published_at;
            return this;
        }

        public Builder withCreatedAt(Timestamp created_at) {
            this.created_at = created_at;
            return this;
        }

        public Builder withUpdatedAt(Timestamp updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }

}
