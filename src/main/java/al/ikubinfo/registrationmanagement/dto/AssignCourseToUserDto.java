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
public class AssignCourseToUserDto {
    private Long id;

    private UserStatusEnum status;

    private double priceReduction;

    private double pricePaid;

    private LocalDate modifiedDate;

    private LocalDate createdDate;

    private RoleDto role;

    private List<CourseDto> courses;
}
