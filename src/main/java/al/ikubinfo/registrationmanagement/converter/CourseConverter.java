package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.dto.CourseUserDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.entity.UserStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
//        dto.setStudents(userConverter.toStudentDtoList(entity.getCourseStudents()));
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

    public CourseUserEntity toCourseUserEntity(CourseUserDto dto) {
        CourseUserEntity entity = new CourseUserEntity();
        entity.setCourse(toEntity(dto.getCourse()));
        entity.setUser(userConverter.toEntity(dto.getUser()));
        entity.setCreatedDate(LocalDate.now());
        entity.setModifiedDate(LocalDate.now());
        entity.setDeleted(false);
        entity.setStatus(UserStatusEnum.REGISTERED);
        return entity;
    }

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

    public CourseEntity toUpdateStudentEntity(CourseDto dto, CourseEntity entity) {
        entity.setId(dto.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        entity.setStartDate(LocalDate.parse(dto.getStartDate().toString(), formatter));
        entity.setEndDate(LocalDate.parse(dto.getEndDate().toString(), formatter));
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public List<CourseDto> toCourseDtoList(List<CourseEntity> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }




}