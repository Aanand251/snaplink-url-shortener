CREATE TABLE audit_logs (

                            id BIGSERIAL PRIMARY KEY,

                            action VARCHAR(50) NOT NULL,

                            performed_by_id BIGINT NOT NULL,

                            performed_by_email VARCHAR(255) NOT NULL,

                            target_user_id BIGINT NOT NULL,

                            target_user_email VARCHAR(255) NOT NULL,

                            remarks VARCHAR(500),

                            created_at TIMESTAMP NOT NULL
);