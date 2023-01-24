package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseDto;
import al.ikubinfo.registrationmanagement.dto.courseDtos.UpdateCourseDto;
import al.ikubinfo.registrationmanagement.dto.courseDtos.ValidatedCourseDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseConverter implements BidirectionalConverter<CourseDto, CourseEntity> {
    @Override
    public CourseDto toDto(CourseEntity entity) {
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
        dto.setPrice(entity.getPrice());
        return dto;
    }
    public UpdateCourseDto toUpdateCourseDto(CourseEntity entity) {
        UpdateCourseDto dto = new UpdateCourseDto();
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

    @Override
    public CourseEntity toEntity(CourseDto dto) {
        return toEntity((ValidatedCourseDto) dto);
    }

    public CourseEntity toEntity(ValidatedCourseDto dto) {
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
        entity.setCreatedDate(entity.getCreatedDate());
        entity.setCourseName(dto.getCourseName());
        entity.setStatus(dto.getStatus());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    public List<CourseDto> toCourseDtoList(List<CourseEntity> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }


}