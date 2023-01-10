package al.ikubinfo.registrationmanagement.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CourseUserDto {

    @NotNull(message = "User is required")
    private Long userId;

    @NotNull(message = "Course is required")
    private Long courseId;

    @NotNull(message = "Status is required")
    private UserStatusEnum status;

    private String reference;

    private String comment;

    @NotNull(message = "Please enter a price paid")
    private Double pricePaid;

    @NotNull(message = "Please enter price reduction")
    private Double priceReduction;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;
}
