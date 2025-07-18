-- add column to cv.work_experience
ALTER TABLE cv.work_experience ADD COLUMN technologies VARCHAR(512);

-- update db_version
UPDATE migration.db_version SET version = 2, applied_on = CURRENT_TIMESTAMP;