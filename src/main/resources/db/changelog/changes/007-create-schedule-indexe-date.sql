--liquibase formatted sql
--changeset module4:7
CREATE INDEX idx_schedule_date  ON schedule(date_start, date_end);
--rollback DROP INDEX idx_schedule_date;