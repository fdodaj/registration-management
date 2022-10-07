package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.dto.CourseUserDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.entity.CourseStatus;
import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseConverter implements BidirectionalConverter<CourseDto, CourseEntity> {

    @Autowired
    private UserConverter userConverter;

    @Override
    public CourseDto toDto(CourseEntity entity) {
        CourseDto dto = new CourseDto();
        dto.setId(entity.getId());
        dto.setCourseName(entity.getCourseName());
        dto.setStatus(entity.getStatus());
        dto.setCourseStartDate(entity.getCourseStartDate());
        dto.setCourseEndDate(entity.getCourseEndDate());
        dto.setRegistrationStartDate(entity.getRegistrationStartDate());
        dto.setRegistrationEndDate(entity.getRegistrationEndDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setPrice(entity.getPrice());
        return dto;
    }


    @Override
    public CourseEntity toEntity(CourseDto dto) {
        CourseEntity entity = new CourseEntity();

        entity.setCourseStartDate(dto.getCourseStartDate());
        entity.setCourseEndDate(dto.getCourseEndDate());
        entity.setRegistrationStartDate(dto.getRegistrationStartDate());
        entity.setRegistrationEndDate(dto.getRegistrationEndDate());
        entity.setCourseName(dto.getCourseName());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : CourseStatus.READY_TO_START);
        entity.setPrice(dto.getPrice());
        return entity;
    }


    public CourseEntity toUpdateCourseEntity(CourseDto dto, CourseEntity entity) {
        entity.setId(dto.getId());
        entity.setCourseStartDate(dto.getCourseStartDate());
        entity.setCourseEndDate(dto.getCourseEndDate());
        entity.setRegistrationStartDate(dto.getRegistrationStartDate());
        entity.setRegistrationEndDate(dto.getRegistrationEndDate());
        entity.setCourseName(dto.getCourseName());
        entity.setStatus(dto.getStatus());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    public List<CourseDto> toCourseDtoList(List<CourseEntity> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }


    // @TODO ??? see again

    public CourseUserDto toCourseUserDto(CourseUserEntity entity) {
        CourseUserDto dto = new CourseUserDto();
        dto.setCourse(toDto(entity.getCourse()));
        dto.setUser(userConverter.toDto(entity.getUser()));
        dto.setCreatedDate(LocalDate.now());
        dto.setModifiedDate(LocalDate.now());
        dto.setDeleted(entity.isDeleted());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}