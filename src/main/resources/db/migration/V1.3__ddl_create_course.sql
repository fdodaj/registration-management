CREATE TABLE course
(
    id                      INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    course_name             VARCHAR(50) NOT NULL,
    price                   INTEGER     NOT NULL,
    status                  VARCHAR(50) NOT NULL,
    course_start_date       TIMESTAMP,
    course_end_date         TIMESTAMP,
    registration_start_date TIMESTAMP   NOT NULL,
    registration_end_date   TIMESTAMP,
    created_date            TIMESTAMP   NOT NULL,
    modified_date           TIMESTAMP   NOT NULL,
    deleted                 BOOLEAN     NOT NULL
);