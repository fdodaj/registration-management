CREATE TABLE course_user
(
    course_id INTEGER NOT NULL,
    user_id   INTEGER NOT NULL,
    PRIMARY KEY (course_id, user_id)
);