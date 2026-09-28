--liquibase formatted sql
--changeset module4:4
CREATE TABLE student (
    id              BIGSERIAL PRIMARY KEY,
    student_name    TEXT NOT NULL,
    student_surname TEXT NOT NULL
);
--rollback DROP TABLE student;