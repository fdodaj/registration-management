package al.ikubinfo.registrationmanagement.dto.courseUserDtos;

import al.ikubinfo.registrationmanagement.dto.BaseDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UserStatusEnum;
import al.ikubinfo.registrationmanagement.entity.CourseUserId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CourseUserDto extends BaseDto {

    @NotNull(message = "User is required")
    private Long userId;

    @NotNull(message = "Course is required")
    private Long courseId;

    private UserStatusEnum status;

    private String reference;

    private String comment;

    private Double pricePaid;

    private Double priceReduction;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;
}
