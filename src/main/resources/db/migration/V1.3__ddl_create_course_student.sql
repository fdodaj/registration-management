CREATE TABLE course_student
(
    course_id  INTEGER NOT NULL,
    student_id INTEGER NOT NULL,
    PRIMARY KEY (course_id, student_id)
);