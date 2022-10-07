package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.CourseConverter;
import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.dto.CourseUserDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserId;
import al.ikubinfo.registrationmanagement.entity.UserStatusEnum;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import al.ikubinfo.registrationmanagement.repository.CourseUserRepository;
import al.ikubinfo.registrationmanagement.repository.UserRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.CourseSpecification;
import al.ikubinfo.registrationmanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseSpecification specification;

    @Autowired
    private UserServiceImpl studentService;

    @Autowired
    private CourseConverter converter;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CourseUserRepository courseUserRepository;


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
    public List<CourseDto> getAllUnfilteredCourses() {
        return converter.toCourseDtoList(courseRepository.findAll());
    }


    @Override
    public void saveCourse(CourseDto courseDto) {
        CourseEntity entity = converter.toEntity(courseDto);
        courseRepository.save(entity);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) {
        CourseEntity currentEntity = getCourseEntity(courseDto.getId());
        CourseEntity entity = converter.toUpdateCourseEntity(courseDto, currentEntity);
        return converter.toDto(courseRepository.save(entity));
    }

    @Override
    public void deleteCourseById(Long id) {
        CourseEntity course = courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Course does not exist"));
        course.setDeleted(true);
        courseRepository.save(course);
    }

    @Override
    public CourseUserDto assignUserToCourse(Long userId, Long courseId) {
        CourseUserEntity entity = new CourseUserEntity();

        CourseUserId id = new CourseUserId();
        id.setCourseId(courseId);
        id.setUserId(userId);
        entity.setId(id);


        entity.setCourse(courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course does not exist")));
        entity.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User does not exist")));
        entity.setStatus(UserStatusEnum.REGISTERED);
        return converter.toCourseUserDto(courseUserRepository.save(entity));
    }

    @Override
    public CourseUserDto removeUserFromCourse(Long userId, Long courseId) {
        CourseUserEntity entity = courseUserRepository.getByUserIdAndCourseId(userId, courseId);
        entity.setDeleted(true);

        return converter.toCourseUserDto(courseUserRepository.save(entity));
    }

    @Override
    public List<CourseUserDto> getAllStudentsByCourseId(Long studentId) {

        return courseUserRepository.getAllByCourseId(studentId)
                .stream()
                .map(converter::toCourseUserDto)
                .collect(Collectors.toList());
    }


    private CourseEntity getCourseEntity(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }


}

