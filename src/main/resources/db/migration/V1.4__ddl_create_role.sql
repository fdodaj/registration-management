CREATE TABLE role
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(50) NOT NULL,
    description   VARCHAR(50) NOT NULL,
    created_date  timestamp   not null,
    modified_date timestamp   not null,
    deleted       BOOLEAN     not null
);
