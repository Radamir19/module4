--liquibase formatted sql
CREATE TABLE groups (
    id         BIGSERIAL PRIMARY KEY,
    group_name TEXT NOT NULL UNIQUE
);