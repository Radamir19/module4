--liquibase formatted sql
CREATE TABLE student (
    id              BIGSERIAL PRIMARY KEY,
    student_name    TEXT NOT NULL,
    student_surname TEXT NOT NULL
);