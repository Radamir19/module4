--liquibase formatted sql
--changeset module4:8
CREATE INDEX idx_schedule_group_id  ON schedule(group_id);
--rollback DROP INDEX idx_schedule_group_id;