package al.ikubinfo.registrationmanagement.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class RoleDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

}
