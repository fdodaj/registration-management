ALTER TABLE course_user
    ADD CONSTRAINT course_user_fk FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE course_user
    ADD CONSTRAINT course_user_fk1 FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE users
    ADD CONSTRAINT user_role FOREIGN KEY (user_role) REFERENCES role (id);
