package al.ikubinfo.registrationmanagement.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Data
@Valid
@Validated
public class UpdateUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

}
