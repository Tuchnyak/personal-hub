-- update db_version
UPDATE migration.db_version SET version = 4, applied_on = CURRENT_TIMESTAMP;

create schema blog authorization dba;

CREATE TABLE IF NOT EXISTS blog.posts
(
    id              UUID PRIMARY KEY,
    title           VARCHAR(1024) NOT NULL,
    slug            VARCHAR(1024) NOT NULL UNIQUE,
    content_html    CLOB NOT NULL,
    is_published    BOOLEAN DEFAULT FALSE NOT NULL,
    published_at    TIMESTAMP,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);