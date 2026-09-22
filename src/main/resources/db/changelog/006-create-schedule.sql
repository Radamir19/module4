--liquibase formatted sql
CREATE TABLE schedule (
    id         BIGSERIAL PRIMARY KEY,
    group_id   BIGINT NOT NULL REFERENCES groups(id),
    course_id  BIGINT NOT NULL REFERENCES course(id),
    date_start TIMESTAMP NOT NULL,
    date_end   TIMESTAMP NOT NULL
);