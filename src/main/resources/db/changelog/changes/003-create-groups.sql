--liquibase formatted sql
--changeset module4:3
CREATE TABLE groups (
    id         BIGSERIAL PRIMARY KEY,
    group_name TEXT NOT NULL UNIQUE
);
--rollback DROP TABLE groups;