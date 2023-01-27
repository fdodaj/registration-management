package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.courseDtos.*;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseConverter implements BidirectionalConverter<CourseDto, CourseEntity> {
    @Override
    public CourseDto toDto(CourseEntity entity) {
        CourseUserConverter converter =new CourseUserConverter();
        CourseDto dto = new CourseDto();
        dto.setId(entity.getId());
        dto.setCourseName(entity.getCourseName());
        dto.setPrice(entity.getPrice());
        dto.setStatus(entity.getStatus());
        dto.setCourseStartDate(entity.getCourseStartDate());
        dto.setCourseEndDate(entity.getCourseEndDate());
        dto.setRegistrationStartDate(entity.getRegistrationStartDate());
        dto.setRegistrationEndDate(entity.getRegistrationEndDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setCourseStudents(converter.toCourseUserDtoList(entity.getCourseUsers()));
        dto.setPrice(entity.getPrice());
        return dto;
    }

    public SimplifiedCourseDto toSimplifiedCourseDto(CourseEntity entity) {
        SimplifiedCourseDto dto = new SimplifiedCourseDto();
        dto.setId(entity.getId());
        dto.setCourseName(entity.getCourseName());
        dto.setPrice(entity.getPrice());
        dto.setStatus(entity.getStatus());
        dto.setCourseStartDate(entity.getCourseStartDate());
        dto.setCourseEndDate(entity.getCourseEndDate());
        dto.setRegistrationStartDate(entity.getRegistrationStartDate());
        dto.setRegistrationEndDate(entity.getRegistrationEndDate());
        dto.setPrice(entity.getPrice());
        return dto;
    }
    public CourseEntity toEntity(CourseDto dto) {
        CourseEntity entity = new CourseEntity();
        entity.setCourseStartDate(dto.getCourseStartDate());
        entity.setCourseEndDate(dto.getCourseEndDate());
        entity.setPrice(dto.getPrice());
        entity.setRegistrationStartDate(dto.getRegistrationStartDate());
        entity.setRegistrationEndDate(dto.getRegistrationEndDate());
        entity.setCourseName(dto.getCourseName());
        entity.setStatus(dto.getStatus());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    public CourseEntity toNewCourseEntity(NewCourseDto dto) {
        CourseEntity entity = new CourseEntity();
        entity.setCourseStartDate(dto.getCourseStartDate());
        entity.setCourseEndDate(dto.getCourseEndDate());
        entity.setPrice(dto.getPrice());
        entity.setRegistrationStartDate(dto.getRegistrationStartDate());
        entity.setRegistrationEndDate(dto.getRegistrationEndDate());
        entity.setCourseName(dto.getCourseName());
        entity.setStatus(dto.getStatus());
        entity.setPrice(dto.getPrice());
        return entity;
    }


    public CourseEntity toUpdateCourseEntity(UpdateCourseDto dto, CourseEntity entity) {
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


}