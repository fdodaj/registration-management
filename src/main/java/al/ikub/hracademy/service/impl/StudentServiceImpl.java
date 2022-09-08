package al.ikub.hracademy.service.impl;

import al.ikub.hracademy.entity.StudentEntity;
import al.ikub.hracademy.repository.StudentRepository;
import al.ikub.hracademy.service.StudentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        super();
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }


    @Override
    public StudentEntity saveStudent(StudentEntity student) {
        student.setDateAdded(LocalDateTime.now());
        student.setLast_modified(LocalDateTime.now());
        student.setDeleted(false);
        return studentRepository.save(student);
    }

    @Override
    public StudentEntity getStudentById(Long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public StudentEntity updateStudent(StudentEntity student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        StudentEntity student = studentRepository.getById(id);
        student.setDeleted(true);
        studentRepository.save(student);
    }

}
