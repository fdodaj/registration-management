package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.StudentDto;
import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.entity.StudentEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentConverter implements BidirectionalConverter<StudentDto, StudentEntity> {

    @Override
    public StudentEntity toEntity(StudentDto dto) {
        StudentEntity entity = new StudentEntity();

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setReference(dto.getReference());
        entity.setStatus(dto.getStatus());
        entity.setPriceReduction(dto.getPriceReduction());
        entity.setPricePaid(dto.getPricePaid());
        entity.setComment(dto.getComment());
//        entity.setCourse(repository.findById(dto.getCourse()).get());
        return entity;
    }


    @Override
    public StudentDto toDto(StudentEntity entity) {
        StudentDto dto = new StudentDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());
        dto.setReference(entity.getReference());
        dto.setPriceReduction(entity.getPriceReduction());
        dto.setPricePaid(entity.getPricePaid());
        dto.setComment(entity.getComment());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }

    public StudentEntity toUpdateStudentEntity(UpdateStudentDto dto, StudentEntity entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setStatus(dto.getStatus());
        entity.setPriceReduction(dto.getPriceReduction());
        entity.setPricePaid(dto.getPricePaid());
        entity.setComment(dto.getComment());
        return entity;
    }

    public List<StudentDto> toStudentDtoList(List<StudentEntity> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

}
