ALTER TABLE users
    ADD COLUMN suspended_by BIGINT;

ALTER TABLE users
    ADD COLUMN suspended_at TIMESTAMP;

ALTER TABLE users
    ADD CONSTRAINT fk_users_suspended_by
        FOREIGN KEY (suspended_by)
            REFERENCES users(id);