CREATE TABLE course
(
    id                      SERIAL PRIMARY KEY,
    course_name             VARCHAR(50) NOT NULL,
    price                   INTEGER     NOT NULL,
    status                  VARCHAR(50) NOT NULL,
    course_start_date       timestamp,
    course_end_date         timestamp,
    registration_start_date timestamp   NOT NULL,
    registration_end_date   timestamp,
    created_date            timestamp   not null,
    modified_date           timestamp   not null,
    deleted                 BOOLEAN     NOT NULL
);