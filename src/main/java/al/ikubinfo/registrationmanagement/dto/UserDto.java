package al.ikubinfo.registrationmanagement.dto;

import al.ikubinfo.registrationmanagement.entity.UserStatusEnum;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Data
@Valid
@Validated
public class UserDto {

    private LocalDate modifiedDate;
    private LocalDate createdDate;
    private RoleDto role;
    private List<CourseDto> courses;
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
