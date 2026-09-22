--liquibase formatted sql
CREATE TABLE teacher (
    id              BIGSERIAL PRIMARY KEY,
    teacher_name    TEXT NOT NULL,
    teacher_surname TEXT NOT NULL
);