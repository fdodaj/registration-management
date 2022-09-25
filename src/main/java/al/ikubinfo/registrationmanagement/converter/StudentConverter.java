package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.StudentDto;
import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.entity.StudentEntity;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentConverter implements BidirectionalConverter<StudentDto, StudentEntity> {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private CourseConverter converter;



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
        dto.setLastModified(entity.getLastModified());
        dto.setLastModified(entity.getLastModified());
        dto.setDateAdded(entity.getDateAdded());
        dto.setDeleted(entity.getDeleted());
        dto.setCourse(entity.getCourses());
        return dto;
    }

    @Override
    public StudentEntity toEntity(StudentDto dto) {
        StudentEntity entity = new StudentEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setReference(dto.getReference());
        entity.setStatus(dto.getStatus());
        entity.setPriceReduction(dto.getPriceReduction());
        entity.setPricePaid(dto.getPricePaid());
        entity.setComment(dto.getComment());
        entity.setLastModified(dto.getLastModified());
        entity.setDateAdded(dto.getDateAdded());
        entity.setDeleted(dto.getDeleted());
//        entity.setCourse(repository.findById(dto.getCourse()).get());
        return entity;
    }

    public UpdateStudentDto toUpdateStudentDto(StudentEntity entity) {
        UpdateStudentDto dto = new UpdateStudentDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());
        dto.setReference(entity.getReference());
        dto.setPricePaid(entity.getPricePaid());
        dto.setComment(entity.getComment());
        dto.setLastModified(entity.getLastModified());
        dto.setLastModified(entity.getLastModified());
        dto.setDeleted(entity.getDeleted());
        return dto;
    }

    public StudentEntity toUpdateStudentEntity(UpdateStudentDto dto) {
        StudentEntity entity = new StudentEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setReference(dto.getReference());
        entity.setStatus(dto.getStatus());
        entity.setPricePaid(dto.getPricePaid());
        entity.setComment(dto.getComment());
        entity.setLastModified(dto.getLastModified());
        entity.setDeleted(dto.getDeleted());
        return entity;
    }


}
