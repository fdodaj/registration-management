package al.ikubinfo.registrationmanagement.dto.userDtos;

import al.ikubinfo.registrationmanagement.dto.BaseDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class SimplifiedUserDto  extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private ReachFormEnum reachForm;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
