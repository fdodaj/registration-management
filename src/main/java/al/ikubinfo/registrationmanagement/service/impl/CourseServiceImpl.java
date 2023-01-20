package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.CourseConverter;
import al.ikubinfo.registrationmanagement.converter.CourseUserConverter;
import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseDto;
import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseStatus;
import al.ikubinfo.registrationmanagement.dto.courseDtos.ValidatedCourseDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.SimplifiedCourseUserDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import al.ikubinfo.registrationmanagement.repository.CourseUserRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.CourseSpecification;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl
        extends ServiceTemplate<CourseCriteria, CourseEntity, CourseRepository, CourseSpecification>
        implements CourseService {
    @Autowired
    CourseSpecification courseSpecification;
    @Autowired
    private CourseConverter converter;
    @Autowired
    private CourseUserConverter courseUserConverter;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseUserRepository courseUserRepository;

    protected CourseServiceImpl(CourseRepository repository, CourseSpecification specificationBuilder) {
        super(repository, specificationBuilder);
    }

    @Override
    public Page<CourseDto> filterCourses(CourseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());
        Specification<CourseEntity> spec = courseSpecification.filter(criteria);
        return courseRepository.findAll(spec, pageable).map(converter::toDto);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) {
        CourseEntity currentEntity = getCourseEntity(courseDto.getId());
        CourseEntity entity = converter.toUpdateCourseEntity(courseDto, currentEntity);
        return converter.toDto(courseRepository.save(entity));
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
    public void deleteCourseById(Long id) {
        courseUserRepository.getCourseUserEntitiesByCourseCourseName(getCourseById(id).getCourseName()).forEach(e -> e.setDeleted(true));
        CourseEntity course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course does not exist"));
        course.setStatus(CourseStatus.FINISHED);
        course.setDeleted(true);
        courseRepository.save(course);
    }

    @Override
    public List<SimplifiedCourseUserDto> getAllStudentsByCourseId(Long courseId) {
        return courseUserRepository.getByIdCourseId(courseId)
                .stream()
                .filter(c -> !c.isDeleted())
                .map(courseUserConverter::toSimplifiedDto)
                .collect(Collectors.toList());
    }

    private CourseEntity getCourseEntity(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public String[] getHeaders() {
        return new String[]{
                "Emri i kursit", "Cmimi", "statusi", "fillimi i regjistrimit", "mbarimi i regjistrimit"
        };
    }

    @Override
    public String[] populate(CourseEntity entity) {
        if (entity.getRegistrationStartDate() == null) {
            entity.setRegistrationStartDate(LocalDate.now());
        }
        if (entity.getRegistrationEndDate() == null) {
            entity.setRegistrationEndDate(LocalDate.now());
        }
        if (entity.getPrice() == null) {
            entity.setPrice((double) 0000);
        }
        return new String[]{
                entity.getCourseName(),
                entity.getPrice().toString(),
                entity.getStatus().name(),
                entity.getRegistrationStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                entity.getRegistrationEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        };
    }
}

