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

    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private UserStatusEnum status;

    private String reference;

    private double priceReduction;

    private double pricePaid;

    public String comment;

    public LocalDate modifiedDate;

    public LocalDate createdDate;

    public RoleDto role;

    public List<CourseDto> courses;
}
