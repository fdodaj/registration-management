package al.ikubinfo.registrationmanagement.dto.courseDtos;

import al.ikubinfo.registrationmanagement.dto.BaseDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UserDto;
import al.ikubinfo.registrationmanagement.validation.DatesCheck;
import al.ikubinfo.registrationmanagement.validation.DatesChecks;
import al.ikubinfo.registrationmanagement.validation.UniqueCourseValidation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Valid
@UniqueCourseValidation(
        courseName = "courseName",
        registrationStartDate = "registrationStartDate"
)

@DatesChecks(value = {
        @DatesCheck(
                first = "courseStartDate",
                second = "courseEndDate",
                message = "courseStartDate must be before courseEndDate"
        ),

        @DatesCheck(
                first = "registrationStartDate",
                second = "registrationEndDate",
                message = "registrationStartDate must be before registrationEndDate"
        )
})
public class NewCourseDto extends BaseDto {
    @NotBlank(message = "Course name cannot be null")
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

    private List<UserDto> courseStudents;
}
