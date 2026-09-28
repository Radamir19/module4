--liquibase formatted sql
--changeset module4:1
CREATE TABLE teacher (
    id              BIGSERIAL PRIMARY KEY,
    teacher_name    TEXT NOT NULL,
    teacher_surname TEXT NOT NULL
);
--rollback DROP TABLE teacher;