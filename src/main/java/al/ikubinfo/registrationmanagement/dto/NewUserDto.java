package al.ikubinfo.registrationmanagement.dto;


import al.ikubinfo.registrationmanagement.entity.UserStatusEnum;
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
    public Long id;

    public String firstName;

    public String lastName;

    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")
    public String phoneNumber;

    @Email
    public String email;

    public String reference;

    public UserStatusEnum status;

    public String comment;

    public LocalDate modifiedDate;

    public LocalDate createdDate;

    public RoleDto role;

    public List<CourseDto> courses;

}
