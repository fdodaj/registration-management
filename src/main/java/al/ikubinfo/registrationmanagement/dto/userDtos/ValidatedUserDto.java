package al.ikubinfo.registrationmanagement.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Valid
public class ValidatedUserDto extends UserDto {


    @NotBlank(message = "First name is required")
    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

}
