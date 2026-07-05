CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE url_mapping
(
    id BIGSERIAL PRIMARY KEY,
    original_url TEXT NOT NULL,
    short_code VARCHAR(20) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP,
    total_clicks BIGINT NOT NULL DEFAULT 0,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_url_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);

CREATE TABLE clickevent
(
    id BIGSERIAL PRIMARY KEY,
    browser VARCHAR(100),
    device VARCHAR(100),
    country VARCHAR(100),
    clicked_at TIMESTAMP NOT NULL,
    url_id BIGINT NOT NULL,

    CONSTRAINT fk_click_url
        FOREIGN KEY (url_id)
            REFERENCES url_mapping(id)
            ON DELETE CASCADE
);

CREATE INDEX idx_short_code
    ON url_mapping(short_code);

CREATE INDEX idx_click_url
    ON clickevent(url_id);

CREATE INDEX idx_user_email
    ON users(email);