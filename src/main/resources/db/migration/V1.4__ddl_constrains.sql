alter table course_student
    add constraint course_student_fk foreign key (student_id) references student (id);
alter table course_student
    add constraint course_student_fk1 foreign key (course_id) references course (id);