package al.ikubinfo.registrationmanagement.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@Data
public class CourseDto {

    private Long id;

    @NotNull
    private String courseName;

    private double price;

    private CourseStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseEndDate;

    @NotNull
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
