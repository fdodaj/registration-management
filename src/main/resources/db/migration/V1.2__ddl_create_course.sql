CREATE TABLE  course
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(50) NOT NULL,
    price          INTEGER not null ,
    status        VARCHAR(50) NOT NULL,
    start_date    timestamp   not null,
    end_date      timestamp   not null,
    created_date  timestamp   not null,
    modified_date timestamp   not null,
    deleted       BOOLEAN     not null
);