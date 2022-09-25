package al.ikubinfo.registrationmanagement.dto;

import al.ikubinfo.registrationmanagement.entity.StudentStatusEnum;
import lombok.Data;

@Data
public class UpdateStudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private StudentStatusEnum status;
    private double priceReduction;
    private double pricePaid;
    private String comment;

}
