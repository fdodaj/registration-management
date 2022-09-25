package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.StudentConverter;
import al.ikubinfo.registrationmanagement.dto.StudentDto;
import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.entity.StudentEntity;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import al.ikubinfo.registrationmanagement.repository.StudentRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.StudentCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.StudentSpecification;
import al.ikubinfo.registrationmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentConverter converter;

    @Autowired
    private StudentSpecification specification;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        super();
        this.studentRepository = studentRepository;
    }


    @Override
    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public boolean saveStudent(StudentDto student) {
        student.setDateAdded(LocalDate.now());
        student.setLastModified(LocalDate.now());
        student.setDeleted(Boolean.FALSE);
        StudentEntity studentEntity = converter.toEntity(student);
        studentRepository.save(studentEntity);
        return true;
    }

    @Override
    public Page<StudentDto> filterStudents(@RequestBody StudentCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());
        Specification<StudentEntity> spec = specification.filter(criteria);


        return studentRepository.findAll(spec, pageable).map(converter::toDto);
    }


    @Override
    public StudentEntity getStudentById(Long id) {

        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Std not found"));
    }

    @Override
    public StudentEntity updateStudent(UpdateStudentDto student) {
        StudentEntity currentStudent = getStudentById(student.getId());
        student.setLastModified(LocalDate.now());
        student.setDeleted(currentStudent.getDeleted());
        StudentEntity studentEntity = converter.toUpdateStudentEntity(student);
        studentEntity.setReference(currentStudent.getReference());
        studentEntity.setDateAdded(currentStudent.getDateAdded());
        studentEntity.setPriceReduction(currentStudent.getPriceReduction());
        return studentRepository.save(studentEntity);
    }

//    @Override
//    public List<StudentEntity> getStudentsByCourseId(Long id) {
//        return studentRepository.getAll();
//
//    }


    @Override
    public void deleteStudentById(Long id) {
        StudentEntity student = studentRepository.findById(id).orElseThrow(null);
        student.setDeleted(true);
        studentRepository.save(student);
    }

}
