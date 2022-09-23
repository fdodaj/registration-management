package al.ikubinfo.registrationmanagement.converter.impl;

import al.ikubinfo.registrationmanagement.converter.CourseConverter;
import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.repository.specification.CourseSpecification;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseConverter converter;

    @Autowired
    CourseSpecification specification;

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
    public Page<CourseDto> filterCourses(@RequestBody CourseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());
        Specification<CourseEntity> spec = specification.filter(criteria);


        return courseRepository.findAll(spec, pageable).map(converter::toDto);
    }

    @Override
    public CourseEntity updateCourse(CourseDto courseDto) {
        CourseEntity currentCourse = getCourseById(courseDto.getId());
        courseDto.setLastModified(LocalDate.now());

        CourseEntity courseEntity = converter.toEntity(courseDto);
        courseEntity.setDeleted(currentCourse.getDeleted());

        courseDto.setDateAdded(currentCourse.getDateAdded());
        return courseRepository.save(courseEntity);
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
