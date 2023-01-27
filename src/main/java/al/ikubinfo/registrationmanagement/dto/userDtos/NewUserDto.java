package al.ikubinfo.registrationmanagement.dto.userDtos;

import al.ikubinfo.registrationmanagement.dto.BaseDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Valid
public class NewUserDto extends BaseDto {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;
    private String email;
    private String phoneNumber;
    private ReachFormEnum reachForm;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
