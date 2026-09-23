--liquibase formatted sql
--changeset module4:2
CREATE TABLE course (
    id          BIGSERIAL PRIMARY KEY,
    course_name TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL,
    teacher_id  BIGINT NOT NULL UNIQUE REFERENCES teacher(id)
);
--rollback DROP TABLE course;