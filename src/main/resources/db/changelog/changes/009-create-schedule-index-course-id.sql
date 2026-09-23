--liquibase formatted sql
--changeset module4:9
CREATE INDEX idx_schedule_course_id ON schedule(course_id);
--rollback DROP INDEX idx_schedule_course_id;