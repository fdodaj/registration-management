package al.ikub.hracademy.converter;

import al.ikub.hracademy.dto.CourseDto;
import al.ikub.hracademy.dto.StudentDto;
import al.ikub.hracademy.entity.CourseEntity;
import al.ikub.hracademy.repository.CourseRepository;
import al.ikub.hracademy.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class CourseConverter implements BidirectionalConverter<CourseDto, CourseEntity> {

    @Autowired
    private StudentRepository repository;

    @Override
    public CourseDto toDto(CourseEntity entity) {
        CourseDto dto = new CourseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDateAdded(entity.getEndDate());
        dto.setLastModified(entity.getLast_modified());
        dto.setDeleted(entity.getDeleted());
        dto.setStudents(entity.getStudents());
        return dto;
    }

    @Override
    public CourseEntity toEntity(CourseDto dto) {
        CourseEntity entity = new CourseEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setDateAdded(dto.getDateAdded());
        entity.setLast_modified(dto.getLastModified());
        entity.setDeleted(entity.getDeleted());
        entity.setStudents(entity.getStudents());
        return entity;
    }
}