package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.CourseUserConverter;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserListDto;
import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserId;
import al.ikubinfo.registrationmanagement.repository.CourseUserRepository;
import al.ikubinfo.registrationmanagement.repository.UserRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseUserCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.CourseSpecification;
import al.ikubinfo.registrationmanagement.repository.specification.CourseUserSpecification;
import al.ikubinfo.registrationmanagement.service.CourseUserService;
import al.ikubinfo.registrationmanagement.service.ServiceTemplate;
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
public class CourseUserServiceImpl extends ServiceTemplate<CourseUserCriteria, CourseUserEntity, CourseUserRepository, CourseUserSpecification> implements CourseUserService {
    @Autowired
    CourseSpecification courseSpecification;
    @Autowired
    CourseUserSpecification courseUserSpecification;
    @Autowired
    private CourseUserConverter courseUserConverter;
    @Autowired
    private CourseUserRepository courseUserRepository;
    @Autowired
    private UserRepository userRepository;

    protected CourseUserServiceImpl(CourseUserRepository repository, CourseUserSpecification specificationBuilder) {
        super(repository, specificationBuilder);
    }


    @Override
    public Page<CourseUserListDto> getCourseUserList(CourseUserCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());

        Specification<CourseUserEntity> spec = courseUserSpecification.filter(criteria);
        return courseUserRepository.findAll(spec, pageable).map(courseUserConverter::toCourseUserList);
    }

    @Override
    public CourseUserDto editCourseUser(CourseUserDto courseUserDto) {
        CourseUserEntity currentCourseUserEntity = courseUserConverter.toEntity(getCourseUserEntity(courseUserDto.getCourseId(), courseUserDto.getUserId()));
        CourseUserEntity entity = courseUserConverter.toUpdateCourseUserEntity(courseUserDto, currentCourseUserEntity);
        entity.setCreatedDate(getCourseUserEntity(courseUserDto.getCourseId(), courseUserDto.getUserId()).getCreatedDate());
        return courseUserConverter.toDto(courseUserRepository.save(entity));
    }

    @Override
    public Long getCourseUserCount() {
        return courseUserRepository.count();
    }

    @Override
    public List<CourseUserListDto> getCourseUserListByCourseId(Long courseId) {
        return courseUserRepository.getCourseUserEntitiesByCourseId(courseId)
                .stream().map(courseUserConverter::toCourseUserList)
                .collect(Collectors.toList());
    }

    @Override
    public CourseUserDto assignUserToCourse(CourseUserDto dto) {
        CourseUserEntity courseUserEntity = courseUserRepository
                .findById(new CourseUserId(dto.getUserId(), dto.getCourseId()))
                .orElse(null);
        if (courseUserEntity != null) {
            courseUserEntity.setDeleted(false);
            courseUserRepository.save(courseUserEntity);
            return courseUserConverter.toDto(courseUserEntity);
        } else {
            dto.setCreatedDate(LocalDate.now());
            dto.setCreatedDate(LocalDate.now());
            userRepository.getById(dto.getUserId()).set_assigned(true);
            courseUserRepository.save(courseUserConverter.toEntity(dto));
            return courseUserConverter.toDto(courseUserConverter.toEntity(dto));
        }
    }

    @Override
    public CourseUserDto getCourseUserEntity(Long courseId, Long userId) {
        return courseUserConverter.toDto(courseUserRepository.findById(new CourseUserId(userId, courseId))
                .orElseThrow(() -> new RuntimeException("Course user relation was not found")));
    }

    @Override
    public void removeUserFromCourse(Long userId, Long courseId) {
        CourseUserEntity entity = courseUserRepository.findByIdCourseIdAndIdUserId(courseId, userId);
        userRepository.getById(userId).set_assigned(false);

        entity.setDeleted(true);
        courseUserRepository.save(entity);
    }

    @Override
    public String[] getHeaders() {
        return new String[]{
                "Emer", "Mbiemer", "Email", "Nr. telefonit", "Emer kursi", "Fillon", "Mbaron", "Statusi", "Comment", "Referenca", "cmimi i paguar", "Ulje cmimi",
        };
    }

    @Override
    public String[] populate(CourseUserEntity entity) {
        if (entity.getPricePaid() == null) {
            entity.setPricePaid(0.0);
        }
        if (entity.getPriceReduction() == null) {
            entity.setPriceReduction(0.0);
        }
        String courseStartDate = "";
        if (entity.getCourse().getCourseStartDate() != null) {
            courseStartDate = entity.getCourse().getCourseStartDate().toString();
        }
        String courseEndDate = "";
        if (entity.getCourse().getCourseEndDate() != null) {
            courseEndDate = entity.getCourse().getCourseEndDate().toString();
        }

        return new String[]{entity.getUser().getFirstName(),
                entity.getUser().getLastName(),
                entity.getUser().getEmail(),
                entity.getUser().getPhoneNumber(),
                entity.getCourse().getCourseName(),
                courseStartDate,
                courseEndDate,
                entity.getCourse().getStatus().toString(),
                entity.getComment(),
                entity.getReference(),
                entity.getPricePaid().toString(),
                entity.getPriceReduction().toString()
        };
    }
}
