package al.ikubinfo.registrationmanagement.dto;

import al.ikubinfo.registrationmanagement.entity.StudentStatusEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
public class StudentDto {

    @NullConstraint
    private Long id;

    @NullConstraint
    @Size(min = 10, max = 50)
    private String firstName;

    @NullConstraint
    @Size(min = 10, max = 50)
    private String lastName;

    @Pattern(regexp = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$\n")
    @NullConstraint
    private String phoneNumber;

    @Email
    @Size(min = 10, max = 100)
    @NullConstraint
    private String email;

    @NullConstraint
    private StudentStatusEnum status;

    @Size(min = 10, max = 50)
    private String reference;

    @Size(min = 10)
    private double priceReduction;

    @Size(min = 10)
    @NullConstraint
    private double pricePaid;

    @Size(min = 10, max = 500)
    private String comment;

    @NullConstraint
    private LocalDate modifiedDate;

    @NullConstraint
    private LocalDate createdDate;

    private List<CourseDto> courses;
}
