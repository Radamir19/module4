--liquibase formatted sql

--changeset module4:1
CREATE TABLE teacher (
                         id              BIGSERIAL PRIMARY KEY,
                         teacher_name    TEXT NOT NULL,
                         teacher_surname TEXT NOT NULL
);

--changeset module4:2
CREATE TABLE course (
                        id          BIGINT PRIMARY KEY REFERENCES teacher(id),
                        course_name TEXT NOT NULL UNIQUE,
                        description TEXT NOT NULL
);

--changeset module4:3
CREATE TABLE class (
                       id         BIGSERIAL PRIMARY KEY,
                       class_name TEXT NOT NULL
);

CREATE TABLE student (
                         id              BIGSERIAL PRIMARY KEY,
                         student_name    TEXT NOT NULL,
                         student_surname TEXT NOT NULL
);

CREATE TABLE class_student (
                               student_id BIGINT REFERENCES student(id) ON DELETE CASCADE,
                               class_id   BIGINT REFERENCES class(id)   ON DELETE CASCADE,
                               PRIMARY KEY (student_id, class_id)
);

--changeset module4:4
CREATE TABLE schedule (
                          id         BIGSERIAL PRIMARY KEY,
                          class_id   BIGINT NOT NULL REFERENCES class(id),
                          course_id  BIGINT NOT NULL REFERENCES course(id),
                          date_start TIMESTAMP NOT NULL,
                          date_end   TIMESTAMP NOT NULL
);

CREATE INDEX idx_schedule_class_id  ON schedule(class_id);
CREATE INDEX idx_schedule_course_id ON schedule(course_id);
CREATE INDEX idx_schedule_date_end  ON schedule(date_end);