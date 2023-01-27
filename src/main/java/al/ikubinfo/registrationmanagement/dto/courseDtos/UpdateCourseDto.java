package al.ikubinfo.registrationmanagement.dto.courseDtos;

import al.ikubinfo.registrationmanagement.dto.BaseDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UserDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateCourseDto extends BaseDto {

    private String courseName;

    private Double price;

    @NotNull(message = "Please select an status")
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

}
