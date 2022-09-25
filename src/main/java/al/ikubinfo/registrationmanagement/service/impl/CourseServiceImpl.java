package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.CourseConverter;
import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.entity.StudentEntity;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.CourseSpecification;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    CourseSpecification specification;
    @Autowired
    private CourseConverter converter;
    @Autowired
    private CourseRepository courseRepository;


    @Override
    public Page<CourseDto> filterCourses(@RequestBody CourseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());
        Specification<CourseEntity> spec = specification.filter(criteria);
        return courseRepository.findAll(spec, pageable).map(converter::toDto);
    }

    @Override
    public CourseDto getCourseById(Long id) {

        return courseRepository.findById(id)
                .map(converter::toDto)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }


    @Override
    public void saveCourse(CourseDto courseDto) {
        CourseEntity entity = converter.toEntity(courseDto);
        courseRepository.save(entity);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) {
        CourseEntity currentEntity = getCourseEntity(courseDto.getId());
        CourseEntity entity = converter.toUpdateStudentEntity(courseDto, currentEntity);
        return converter.toDto(courseRepository.save(entity));
    }

    @Override
    public void deleteCourseById(Long id) {
        CourseEntity course = courseRepository.findById(id).orElseThrow(null);
        course.setDeleted(true);
        courseRepository.save(course);
    }

    private CourseEntity getCourseEntity(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public void addStudentToCourse(Long studentId, Long courseId) {
        CourseEntity course = getCourseEntity(courseId);
        StudentEntity studentEntity = studentService.getStudentEntity(studentId);

        course.getCourseStudents().add(studentEntity);
        courseRepository.save(course);


    }
}
