package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.StudentConverter;
import al.ikubinfo.registrationmanagement.dto.StudentDto;
import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.entity.StudentEntity;
import al.ikubinfo.registrationmanagement.repository.StudentRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.StudentCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.StudentSpecification;
import al.ikubinfo.registrationmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentConverter studentConverter;

    @Autowired
    private StudentSpecification specification;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Page<StudentDto> filterStudents(@RequestBody StudentCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());
        return studentRepository
                .findAll(specification.filter(criteria), pageable)
                .map(studentConverter::toDto);
    }

    @Override
    public StudentDto getStudentById(Long id) {
        return studentConverter.toDto(getStudentEntity(id));

    }

    @Override
    public void saveStudent(StudentDto dto) {
        StudentEntity studentEntity = studentConverter.toEntity(dto);
        studentRepository.save(studentEntity);
    }

    @Override
    public StudentDto updateStudent(UpdateStudentDto student) {
        StudentEntity currentEntity = getStudentEntity(student.getId());
        StudentEntity studentEntity = studentConverter.toUpdateStudentEntity(student, currentEntity);
        return studentConverter.toDto(studentRepository.save(studentEntity));
    }


    @Override
    public void deleteStudentById(Long id) {
        StudentEntity student = getStudentEntity(id);
        student.setDeleted(true);
        studentRepository.save(student);
    }

    private StudentEntity getStudentEntity(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

}
