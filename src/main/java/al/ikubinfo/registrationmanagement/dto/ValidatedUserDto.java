package al.ikubinfo.registrationmanagement.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;


@Valid
@Data
public class ValidatedUserDto {

    private LocalDate modifiedDate;
    private LocalDate createdDate;
    private RoleDto role;
    private List<CourseDto> courses;
    private Long id;
    @NotBlank(message = "first name is required")

    private String firstName;

    @NotBlank(message = "last name is required")
    private String lastName;

    @NotBlank(message = "phone number is required")

    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "please enter the right number format")
    private String phoneNumber;

    @NotBlank(message = "email is required")
    @Email(message = "please enter an correct email")
    private String email;
}
