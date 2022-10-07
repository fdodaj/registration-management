CREATE TABLE  users
(
    id              SERIAL PRIMARY KEY,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    phone_number    VARCHAR(50) NOT NULL,
    email           VARCHAR(50) NOT NULL,
    password        VARCHAR(255),
    created_date    timestamp   not null,
    modified_date   timestamp   not null,
    user_role       SERIAL     NOT NULL,
    deleted         BOOLEAN     not null
);
