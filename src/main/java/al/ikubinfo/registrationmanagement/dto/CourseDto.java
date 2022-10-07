package al.ikubinfo.registrationmanagement.dto;

import al.ikubinfo.registrationmanagement.entity.CourseStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;


@Data
public class CourseDto {

    private Long id;

    private String courseName;

    private double price;

    private CourseStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseEndDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationEndDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

    private List<UserDto> students;
}
