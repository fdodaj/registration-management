package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.CourseUserList;
import al.ikubinfo.registrationmanagement.dto.SimplifiedCourseUserDto;
import al.ikubinfo.registrationmanagement.dto.UserStatusEnum;
import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserId;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import al.ikubinfo.registrationmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        return dto;
    }

    public SimplifiedCourseUserDto toSimplifiedDto(CourseUserEntity entity) {
        SimplifiedCourseUserDto dto = new SimplifiedCourseUserDto();
        dto.setUserId(entity.getId().getUserId());
        dto.setCourseDto(courseConverter.toDto(entity.getCourse()));
        dto.setUserDto(userConverter.toDto(entity.getUser()));
        dto.setStatus(entity.getStatus());
        return dto;
    }

    @Override
    public CourseUserEntity toEntity(CourseUserDto dto) {
        CourseUserEntity entity = new CourseUserEntity();
        CourseUserId id = new CourseUserId(dto.getUserId(), dto.getCourseId());
        entity.setId(id);
        entity.setCourse(courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course does not exist")));
        entity.setUser(userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User does not exist")));
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
        entity.setPriceReduction(dto.getPriceReduction());
        entity.setPricePaid(dto.getPricePaid());
        entity.setComment(dto.getComment());
        entity.setStatus(dto.getStatus());
        entity.setReference(dto.getReference());
        return entity;
    }

    public CourseUserList toCourseUserList(CourseUserEntity entity) {
        CourseUserList dto = new CourseUserList();
        dto.setCourseDto(courseConverter.toDto(entity.getCourse()));
        dto.setUserDto(userConverter.toDto(entity.getUser()));
        dto.setStatus(entity.getStatus());
        dto.setReference(entity.getReference());
        dto.setComment(entity.getComment());
        dto.setPricePaid(entity.getPricePaid());
        dto.setPriceReduction(entity.getPriceReduction());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
}
