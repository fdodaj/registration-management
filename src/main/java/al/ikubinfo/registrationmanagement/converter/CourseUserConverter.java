package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserListDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.SimplifiedCourseUserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UserStatusEnum;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserId;
import al.ikubinfo.registrationmanagement.entity.UserEntity;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import al.ikubinfo.registrationmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseUserConverter implements BidirectionalConverter<CourseUserDto, CourseUserEntity> {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseConverter courseConverter;

    @Autowired
    private UserConverter userConverter;

    @Override
    public CourseUserDto toDto(CourseUserEntity entity) {
        CourseUserDto dto = new CourseUserDto();
        dto.setCourseId(entity.getId().getCourseId());
        dto.setUserId(entity.getId().getUserId());
        dto.setComment(entity.getComment());
        dto.setReference(entity.getReference());
        dto.setPricePaid(entity.getPricePaid());
        dto.setPriceReduction(entity.getPriceReduction());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(LocalDate.now());
        return dto;
    }

    public SimplifiedCourseUserDto toSimplifiedDto(CourseUserEntity entity) {
        SimplifiedCourseUserDto dto = new SimplifiedCourseUserDto();
        dto.setStudentName(entity.getUser().getFirstName() + " " + entity.getUser().getLastName());
        dto.setCourseName(entity.getCourse().getCourseName());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    @Override
    public CourseUserEntity toEntity(CourseUserDto dto) {
        CourseUserEntity entity = new CourseUserEntity();
        CourseUserId id = new CourseUserId(dto.getUserId(), dto.getCourseId());
        entity.setId(id);
        entity.setCourse(courseRepository.findById(dto.getCourseId()).orElseThrow(() -> new RuntimeException("Course does not exist")));
        entity.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User does not exist")));
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : UserStatusEnum.INTERESTED);
        entity.setComment(dto.getComment());
        entity.setPricePaid(dto.getPricePaid());
        entity.setPriceReduction(dto.getPriceReduction());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setReference(dto.getReference());
        return entity;
    }

    public CourseUserEntity toUpdateCourseUserEntity(CourseUserDto dto, CourseUserEntity entity) {
        entity.setId(new CourseUserId(dto.getUserId(), dto.getCourseId()));
        UserEntity user = userRepository.findById(dto.getUserId()).orElseThrow(null);
        CourseEntity course = courseRepository.findById(dto.getCourseId()).orElseThrow(null);
        entity.setUser(user);
        entity.setCourse(course);
        entity.setPriceReduction(dto.getPriceReduction());
        entity.setPricePaid(dto.getPricePaid());
        entity.setComment(dto.getComment());
        entity.setStatus(dto.getStatus());
        entity.setReference(dto.getReference());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }

    public CourseUserListDto toCourseUserList(CourseUserEntity entity) {
        CourseUserListDto dto = new CourseUserListDto();
        dto.setId(entity.getId());
        dto.setCourseDto(courseConverter.toSimplifiedUserDto(entity.getCourse()));
        dto.setUserDto(userConverter.toSimplifiedUserDto(entity.getUser()));
        dto.setStatus(entity.getStatus());
        dto.setReference(entity.getReference());
        dto.setComment(entity.getComment());
        dto.setPricePaid(entity.getPricePaid());
        dto.setPriceReduction(entity.getPriceReduction());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }

    public List<SimplifiedCourseUserDto> toCourseUserDtoList(List<CourseUserEntity> entities) {
        return entities.stream().map(this::toSimplifiedDto).collect(Collectors.toList());
    }
}
