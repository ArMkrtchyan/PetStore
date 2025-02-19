CREATE TABLE users
(
    id          BIGINT              NOT NULL,
    fullname    VARCHAR(255),
    username    VARCHAR(255),
    password    VARCHAR(255),
    phone       VARCHAR(255) UNIQUE NOT NULL,
    email       VARCHAR(255) UNIQUE,
    birthday    VARCHAR(255),
    photo       VARCHAR(255),
    gender      VARCHAR(255),
    firebase_id VARCHAR(255),
    created_at  timestamp,
    updated_at  timestamp,

    PRIMARY KEY (id)
);

CREATE TABLE device
(
    id                 BIGINT              NOT NULL,
    first_install_date VARCHAR(255),
    uid                VARCHAR(255) UNIQUE NOT NULL,
    language           VARCHAR(255),
    model              VARCHAR(255),
    platform           VARCHAR(255),
    firebase_token     VARCHAR(255),
    sdk_version        VARCHAR(255) UNIQUE NOT NULL,
    app_version        VARCHAR(255) UNIQUE NOT NULL,
    created_at         timestamp,
    updated_at         timestamp,

    PRIMARY KEY (id)
);

CREATE TABLE pets
(
    id         BIGINT NOT NULL,
    name       VARCHAR(2024),
    created_at timestamp,
    updated_at timestamp,
    deleted_at timestamp,

    PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id         BIGINT NOT NULL,
    name       VARCHAR(2024),
    created_at timestamp,
    updated_at timestamp,
    deleted_at timestamp,

    PRIMARY KEY (id)
);

CREATE TABLE products
(
    id         BIGINT NOT NULL,
    name       VARCHAR(2024),
    created_at timestamp,
    updated_at timestamp,
    deleted_at timestamp,

    PRIMARY KEY (id)
);

CREATE TABLE store
(
    id         BIGINT NOT NULL,
    name       VARCHAR(2024),
    created_at timestamp,
    updated_at timestamp,
    deleted_at timestamp,

    PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id         BIGINT NOT NULL,
    name       VARCHAR(2024),
    created_at timestamp,
    updated_at timestamp,

    PRIMARY KEY (id)
);