package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.CourseConverter;
import al.ikubinfo.registrationmanagement.converter.CourseUserConverter;
import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.dto.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.SimplifiedCourseUserDto;
import al.ikubinfo.registrationmanagement.dto.ValidatedCourseDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserId;
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseSpecification specification;

    @Autowired
    private CourseConverter converter;

    @Autowired
    private CourseUserConverter courseUserConverter;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseUserRepository courseUserRepository;


    @Override
    public Page<CourseDto> filterCourses(CourseCriteria criteria) {
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
    public CourseDto saveCourse(ValidatedCourseDto courseDto) {
        CourseEntity entity = converter.toEntity(courseDto);
        return converter.toDto(courseRepository.save(entity));
    }

    @Override
    public CourseDto updateCourse(ValidatedCourseDto courseDto) {
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
    public CourseUserDto assignUserToCourse(CourseUserDto dto) {
        dto.setCreatedDate(LocalDate.now());
        dto.setCreatedDate(LocalDate.now());
        courseUserRepository.save(courseUserConverter.toEntity(dto));
        return courseUserConverter.toDto(courseUserConverter.toEntity(dto));
    }

    @Override
    public void removeUserFromCourse(Long userId, Long courseId) {
        CourseUserEntity entity = courseUserRepository.findByIdCourseIdAndUserId(courseId, userId);
        entity.setDeleted(true);
    }

    @Override
    public CourseUserDto updateCourseUser(CourseUserDto dto) {
        CourseUserEntity currentEntity = getCourseUserEntity(dto.getUserId(), dto.getCourseId());
        CourseUserEntity entity = courseUserConverter.toUpdateCourseUserEntity(dto, currentEntity);
        return courseUserConverter.toDto(courseUserRepository.save(entity));
    }

    @Override
    public List<SimplifiedCourseUserDto> getAllStudentsByCourseId(Long courseId) {
        return courseUserRepository.getByIdCourseId(courseId)
                .stream()
                .map(courseUserConverter::toSimplifiedDto)
                .collect(Collectors.toList());
    }


    private CourseEntity getCourseEntity(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    private CourseUserEntity getCourseUserEntity(Long courseId, Long userId) {
        return courseUserRepository.findById(new CourseUserId(userId, courseId))
                .orElseThrow(() -> new RuntimeException("Course user relation was not found"));
    }


}

