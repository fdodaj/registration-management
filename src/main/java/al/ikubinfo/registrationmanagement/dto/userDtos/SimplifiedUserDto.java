package al.ikubinfo.registrationmanagement.dto.userDtos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class SimplifiedUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private ReachFormEnum reachForm;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
