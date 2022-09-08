package al.ikub.hracademy.service;

import al.ikub.hracademy.entity.StudentEntity;

import java.util.List;

public interface StudentService {
    List<StudentEntity> getAllStudents();

    StudentEntity saveStudent(StudentEntity student);

    StudentEntity getStudentById(Long id);

    StudentEntity updateStudent(StudentEntity student);



    void deleteStudentById(Long id);
}
