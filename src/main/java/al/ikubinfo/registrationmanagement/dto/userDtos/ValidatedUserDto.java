package al.ikubinfo.registrationmanagement.dto.userDtos;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@Valid
public class ValidatedUserDto extends UserDto {


    @NotBlank(message = "First name is required")
    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

}
