package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.StudentDto;
import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.repository.criteria.StudentCriteria;
import org.springframework.data.domain.Page;

public interface StudentService {

    Page<StudentDto> filterStudents(StudentCriteria criteria);

    StudentDto getStudentById(Long id);

    void saveStudent(StudentDto student);

    StudentDto updateStudent(UpdateStudentDto student);

    void deleteStudentById(Long id);
}
