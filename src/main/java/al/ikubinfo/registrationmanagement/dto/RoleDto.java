package al.ikubinfo.registrationmanagement.dto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;

@Data
@Valid
@Validated
public class RoleDto {

    @NullConstraint
    private Long id;

    @NullConstraint
    private String name;

    @NullConstraint
    private String description;

    @NullConstraint
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @NullConstraint
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

}
