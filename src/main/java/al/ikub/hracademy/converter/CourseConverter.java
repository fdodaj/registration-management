package al.ikub.hracademy.converter;

import al.ikub.hracademy.dto.CourseDto;
import al.ikub.hracademy.entity.CourseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

public class CourseConverter implements BidirectionalConverter<CourseDto, CourseEntity> {


    @Override
    public CourseDto toDto(CourseEntity entity) {
        CourseDto dto = new CourseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDateAdded(entity.getEndDate());
        dto.setLastModified(entity.getLastModified());
        dto.setDeleted(entity.getDeleted());
//        dto.setStudents(entity.getStudents());
        return dto;
    }

    public List<CourseDto> toDtoList(CourseEntity entity) {
        List<CourseDto> dtoList =new ArrayList<>();
        CourseDto dto = new CourseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDateAdded(entity.getEndDate());
        dto.setLastModified(entity.getLastModified());
        dto.setDeleted(entity.getDeleted());
        dtoList.add(dto);
        return dtoList;
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
        entity.setLastModified(dto.getLastModified());
        entity.setDeleted(entity.getDeleted());
//        entity.setStudents(entity.getStudents());
        return entity;
    }
}