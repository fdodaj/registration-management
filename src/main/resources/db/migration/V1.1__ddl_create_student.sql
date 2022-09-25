CREATE TABLE IF NOT EXISTS student
(
    id              SERIAL PRIMARY KEY,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    phone_number    VARCHAR(50) NOT NULL,
    email           VARCHAR(50) NOT NULL,
    status          VARCHAR(50),
    reference       VARCHAR(50),
    price_reduction numeric,
    price_paid      numeric,
    comment         VARCHAR(100),
    created_date    timestamp   not null,
    modified_date   timestamp   not null,
    deleted         BOOLEAN     not null
);
