package al.ikub.hracademy.service.impl;

import al.ikub.hracademy.converter.CourseConverter;
import al.ikub.hracademy.dto.CourseDto;
import al.ikub.hracademy.entity.CourseEntity;
import al.ikub.hracademy.entity.StudentEntity;
import al.ikub.hracademy.repository.CourseRepository;
import al.ikub.hracademy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseConverter converter;

    @Autowired
    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        super();
        this.courseRepository = courseRepository;
    }


    @Override
    public boolean saveCourse(CourseDto course) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        course.setDateAdded(LocalDate.now());
        course.setLastModified(LocalDate.now());
        course.setStartDate(LocalDate.parse(course.getStartDate().toString(), formatter));
        course.setEndDate(LocalDate.parse(course.getEndDate().toString(), formatter));
        CourseEntity courseEntity = converter.toEntity(course);
        courseEntity.setDeleted(false);
        courseRepository.save(courseEntity);
        return true;
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public CourseEntity updateCourse(CourseDto courseDto) {
        CourseEntity currentCourse = getCourseById(courseDto.getId());
        courseDto.setLastModified(LocalDate.now());;
        courseDto.setDeleted(currentCourse.getDeleted());
        CourseEntity studentEntity = converter.toEntity(courseDto);
        courseDto.setDateAdded(currentCourse.getDateAdded());
        return courseRepository.save(studentEntity);
    }

    @Override
    public CourseEntity getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public void deleteCourseById(Long id) {
        CourseEntity course = courseRepository.findById(id).orElseThrow(null);
        course.setDeleted(true);
        courseRepository.save(course);
    }

}
