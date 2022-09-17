package al.ikub.hracademy.service;

import al.ikub.hracademy.dto.StudentDto;
import al.ikub.hracademy.dto.UpdateStudentDto;
import al.ikub.hracademy.entity.StudentEntity;

import java.util.List;

public interface StudentService {
    List<StudentEntity> getAllStudents();

    boolean saveStudent(StudentDto student);

    StudentEntity getStudentById(Long id);

    StudentEntity updateStudent(UpdateStudentDto student);

    List<StudentEntity> getStudentsByCourseId(Long id);

    void deleteStudentById(Long id);
}
