create schema blog authorization dba;

CREATE TABLE IF NOT EXISTS blog.posts
(
    id              VARCHAR(36)   PRIMARY KEY,
    title           VARCHAR(1024) NOT NULL,
    slug            VARCHAR(1024) NOT NULL UNIQUE,
    content_html    LONGVARCHAR NOT NULL,
    is_published    BOOLEAN DEFAULT FALSE NOT NULL,
    published_at    TIMESTAMP(9),
    created_at      TIMESTAMP(9) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP(9) DEFAULT CURRENT_TIMESTAMP NOT NULL
);
