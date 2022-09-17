package al.ikub.hracademy.service.impl;

import al.ikub.hracademy.converter.StudentConverter;
import al.ikub.hracademy.dto.StudentDto;
import al.ikub.hracademy.dto.UpdateStudentDto;
import al.ikub.hracademy.entity.StudentEntity;
import al.ikub.hracademy.repository.CourseRepository;
import al.ikub.hracademy.repository.StudentRepository;
import al.ikub.hracademy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentConverter converter;

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
        studentEntity.setCourse(courseRepository.findById(student.getCourse()).get());
        studentRepository.save(studentEntity);
        return true;
    }

    @Override
    public StudentEntity getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Std not found"));
    }

    @Override
    public StudentEntity updateStudent(UpdateStudentDto student) {
        StudentEntity currentStudent = getStudentById(student.getId());
        student.setLastModified(LocalDate.now());;
        student.setDeleted(currentStudent.getDeleted());
        StudentEntity studentEntity = converter.toUpdateStudentEntity(student);
        studentEntity.setReference(currentStudent.getReference());
        studentEntity.setDateAdded(currentStudent.getDateAdded());
        studentEntity.setPriceReduction(currentStudent.getPriceReduction());
        return studentRepository.save(studentEntity);
    }

    @Override
    public List<StudentEntity> getStudentsByCourseId(Long id) {
        return new ArrayList<>(studentRepository
                .getAllByCourseId(id));
    }

    @Override
    public void deleteStudentById(Long id) {
      StudentEntity student = studentRepository.findById(id).orElseThrow(null);
      student.setDeleted(true);
      studentRepository.save(student);
    }

}
