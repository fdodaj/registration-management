alter table course_user
    add constraint course_user_fk foreign key (user_id) references "users" (id);
alter table course_user
    add constraint course_user_fk1 foreign key (course_id) references course (id);

ALTER TABLE users ADD CONSTRAINT "user_role" FOREIGN KEY ("user_role") REFERENCES role (id);
