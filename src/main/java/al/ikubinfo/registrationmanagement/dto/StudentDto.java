package al.ikubinfo.registrationmanagement.dto;

import al.ikubinfo.registrationmanagement.entity.StudentStatusEnum;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Valid
@Validated
public class StudentDto {

    @NullConstraint
    private Long id;

    @NullConstraint
    @Size(min = 10, max = 50)
    private String firstName;

    @NullConstraint
    @Size(min = 10, max = 50)
    private String lastName;

    @Pattern(regexp =  "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")
    private String phoneNumber;

    @Size(min = 10, max = 100)
    @NullConstraint
    @Email
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

    @Size(max = 500)
    private String comment;

    @NullConstraint
    private LocalDate modifiedDate;

    @NullConstraint
    private LocalDate createdDate;

    private List<CourseDto> courses;
}
