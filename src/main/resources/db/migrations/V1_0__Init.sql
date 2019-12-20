CREATE TABLE users
(
    id          BIGINT              NOT NULL,
    fullname    VARCHAR(255),
    password    VARCHAR(2024),
    phone       VARCHAR(255) UNIQUE NOT NULL,
    email       VARCHAR(255) UNIQUE,
    firebase_id VARCHAR(255),
    created_at  timestamp,
    updated_at  timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE device
(
    id             BIGINT              NOT NULL,
    uid            VARCHAR(255) UNIQUE NOT NULL,
    language       VARCHAR(255),
    model          VARCHAR(255),
    platform       VARCHAR(255),
    firebase_token VARCHAR(255),
    sdk_version    VARCHAR(255) UNIQUE NOT NULL,
    app_version    VARCHAR(255) UNIQUE NOT NULL,
    created_at     timestamp,
    updated_at     timestamp,
    PRIMARY KEY (id)
);