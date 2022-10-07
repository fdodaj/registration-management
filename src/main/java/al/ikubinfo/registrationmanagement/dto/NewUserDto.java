package al.ikubinfo.registrationmanagement.dto;


import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
@Valid
@Validated
public class NewUserDto {
    private Long id;

    private String firstName;

    private String lastName;

    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")
    private String phoneNumber;

    @Email
    private String email;

    private LocalDate modifiedDate;

    private LocalDate createdDate;

    private RoleDto role;

    private List<CourseDto> courses;

}
