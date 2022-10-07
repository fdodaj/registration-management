CREATE TABLE users
(
    id            INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    phone_number  VARCHAR(50) NOT NULL,
    email         VARCHAR(50) NOT NULL,
    password      VARCHAR(255),
    created_date  TIMESTAMP   NOT NULL,
    modified_date TIMESTAMP   NOT NULL,
    user_role     INTEGER     NOT NULL,
    deleted       BOOLEAN     NOT NULL
);
