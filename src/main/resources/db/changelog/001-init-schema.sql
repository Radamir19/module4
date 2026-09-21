--liquibase formatted sql

--changeset module4:1
CREATE TABLE teacher (
                         id              BIGSERIAL PRIMARY KEY,
                         teacher_name    TEXT NOT NULL,
                         teacher_surname TEXT NOT NULL
);

--changeset module4:2
CREATE TABLE course (
                        id          BIGSERIAL PRIMARY KEY,
                        course_name TEXT NOT NULL UNIQUE,
                        description TEXT NOT NULL,
                        teacher_id  BIGINT NOT NULL UNIQUE REFERENCES teacher(id)
);

--changeset module4:3
CREATE TABLE groups (
                        id         BIGSERIAL PRIMARY KEY,
                        group_name TEXT NOT NULL
);

CREATE TABLE student (
                         id              BIGSERIAL PRIMARY KEY,
                         student_name    TEXT NOT NULL,
                         student_surname TEXT NOT NULL
);

CREATE TABLE student_group (
                               student_id BIGINT REFERENCES student(id) ON DELETE CASCADE,
                               group_id   BIGINT REFERENCES groups(id)  ON DELETE CASCADE,
                               PRIMARY KEY (student_id, group_id)
);

--changeset module4:4
CREATE TABLE schedule (
                          id         BIGSERIAL PRIMARY KEY,
                          group_id   BIGINT NOT NULL REFERENCES groups(id),
                          course_id  BIGINT NOT NULL REFERENCES course(id),
                          date_start TIMESTAMP NOT NULL,
                          date_end   TIMESTAMP NOT NULL
);

CREATE INDEX idx_schedule_group_id  ON schedule(group_id);
CREATE INDEX idx_schedule_course_id ON schedule(course_id);
CREATE INDEX idx_schedule_date_end  ON schedule(date_end);