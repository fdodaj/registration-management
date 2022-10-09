CREATE TABLE users
(
    id            serial PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    phone_number  VARCHAR(20) UNIQUE NOT NULL,
    email         VARCHAR(50) UNIQUE NOT NULL,
    password      VARCHAR(255),
    reach_form    VARCHAR(50),
    birth_date    DATE,
    created_date  TIMESTAMP   NOT NULL,
    modified_date TIMESTAMP   NOT NULL,
    user_role     INTEGER     NOT NULL,
    deleted       BOOLEAN     NOT NULL
);
