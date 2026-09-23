--liquibase formatted sql
--changeset module4:5
CREATE TABLE student_group (
    student_id BIGINT REFERENCES student(id) ON DELETE CASCADE,
    group_id   BIGINT REFERENCES groups(id)  ON DELETE CASCADE,
    PRIMARY KEY (student_id, group_id)
);
--rollback DROP TABLE student_group;