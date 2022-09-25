package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.StudentDto;
import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.entity.StudentEntity;
import al.ikubinfo.registrationmanagement.repository.criteria.StudentCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    List<StudentEntity> getAllStudents();

    boolean saveStudent(StudentDto student);


    Page<StudentDto> filterStudents(StudentCriteria criteria);

    StudentEntity getStudentById(Long id);

    StudentEntity updateStudent(UpdateStudentDto student);

//    List<StudentEntity> getStudentsByCourseId(Long id);

    void deleteStudentById(Long id);
}
