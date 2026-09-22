--liquibase formatted sql
CREATE TABLE course (
    id          BIGSERIAL PRIMARY KEY,
    course_name TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL,
    teacher_id  BIGINT NOT NULL UNIQUE REFERENCES teacher(id)
);