--liquibase formatted sql
--changeset module4:6
CREATE TABLE schedule (
    id         BIGSERIAL PRIMARY KEY,
    group_id   BIGINT NOT NULL REFERENCES groups(id),
    course_id  BIGINT NOT NULL REFERENCES course(id),
    date_start TIMESTAMPZ NOT NULL,
    date_end   TIMESTAMPZ NOT NULL
);
--rollback DROP TABLE schedule;