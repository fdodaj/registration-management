CREATE TABLE role
(
    id            serial PRIMARY KEY ,
    name          VARCHAR(50) NOT NULL,
    description   VARCHAR(50) NOT NULL,
    created_date  TIMESTAMP   NOT NULL,
    modified_date TIMESTAMP   NOT NULL,
    deleted       BOOLEAN     NOT NULL
);

