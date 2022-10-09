package al.ikubinfo.registrationmanagement.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;


@Valid
public class ValidatedUserDto extends UserDto {

    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}",
            message = "Phone number is not in the right format")
    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @NotBlank(message = "email is required")
    @Email(message = "please enter an correct email")
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @NotBlank(message = "First name is required")
    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @NotBlank(message = "Last name is required")
    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @NotBlank(message = "BirthDate is required")
    @Override
    public LocalDate getBirthDate() {
        return super.getBirthDate();
    }
}
