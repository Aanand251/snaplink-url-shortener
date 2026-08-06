ALTER TABLE users
    ADD COLUMN marked BOOLEAN NOT NULL DEFAULT FALSE,

    ADD COLUMN marked_reason VARCHAR(50),

    ADD COLUMN marked_at TIMESTAMP,

    ADD COLUMN marked_by BIGINT,

    ADD COLUMN suspended BOOLEAN NOT NULL DEFAULT FALSE,

    ADD COLUMN suspension_type VARCHAR(20),

    ADD COLUMN suspended_until TIMESTAMP;

ALTER TABLE users
    ADD CONSTRAINT fk_users_marked_by
        FOREIGN KEY (marked_by)
            REFERENCES users(id)
            ON DELETE SET NULL;