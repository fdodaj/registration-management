package al.ikubinfo.registrationmanagement.dto;

import al.ikubinfo.registrationmanagement.entity.StudentStatusEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateStudentDto {
    public Long id;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public StudentStatusEnum status;
    public String reference;
    public double pricePaid;
    public String comment;
    public LocalDate lastModified;
    public Boolean deleted;
    private CourseDto course;

}
