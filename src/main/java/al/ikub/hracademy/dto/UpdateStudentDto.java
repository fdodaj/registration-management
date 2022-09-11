package al.ikub.hracademy.dto;

import al.ikub.hracademy.entity.StudentStatusEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateStudentDto {
    public long id;
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
}
