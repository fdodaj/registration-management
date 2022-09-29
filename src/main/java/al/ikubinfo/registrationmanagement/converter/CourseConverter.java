package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CourseConverter implements BidirectionalConverter<CourseDto, CourseEntity> {

    @Autowired
    private UserConverter userConverter;

    @Override
    public CourseDto toDto(CourseEntity entity) {
        CourseDto dto = new CourseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setStudents(userConverter.toStudentDtoList(entity.getCourseStudents()));
        return dto;
    }


    @Override
    public CourseEntity toEntity(CourseDto dto) {
        CourseEntity entity = new CourseEntity();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        entity.setStartDate(LocalDate.parse(dto.getStartDate().toString(), formatter));
        entity.setEndDate(LocalDate.parse(dto.getEndDate().toString(), formatter));
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public CourseEntity toUpdateStudentEntity(CourseDto dto, CourseEntity entity) {
        entity.setId(dto.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        entity.setStartDate(LocalDate.parse(dto.getStartDate().toString(), formatter));
        entity.setEndDate(LocalDate.parse(dto.getEndDate().toString(), formatter));
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}