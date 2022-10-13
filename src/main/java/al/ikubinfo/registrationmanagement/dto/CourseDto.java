package al.ikubinfo.registrationmanagement.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@Data
public class CourseDto {

    private Long id;

    @NotBlank(message = "Course name cannot be null")
    private String courseName;

    @NotNull(message = "price can't  be null")
    private Double price;

    @NotNull
    private CourseStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseEndDate;

    @NotNull(message = "Registration start date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationEndDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

    private List<UserDto> courseStudents;
}
