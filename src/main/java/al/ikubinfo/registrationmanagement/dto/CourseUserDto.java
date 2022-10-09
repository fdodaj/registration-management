package al.ikubinfo.registrationmanagement.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CourseUserDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long courseId;

    private UserStatusEnum status;

    private String reference;

    private String comment;

    private double pricePaid;

    private double priceReduction;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;
}
