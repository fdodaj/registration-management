package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.StudentDto;
import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.entity.StudentEntity;

import java.util.List;

public interface StudentService {
    List<StudentEntity> getAllStudents();

    boolean saveStudent(StudentDto student);

    StudentEntity getStudentById(Long id);

    StudentEntity updateStudent(UpdateStudentDto student);

    List<StudentEntity> getStudentsByCourseId(Long id);

    void deleteStudentById(Long id);
}
