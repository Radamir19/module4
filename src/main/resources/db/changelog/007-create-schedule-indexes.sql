--liquibase formatted sql
CREATE INDEX idx_schedule_group_id  ON schedule(group_id);
CREATE INDEX idx_schedule_course_id ON schedule(course_id);
CREATE INDEX idx_schedule_date_end  ON schedule(date_end);