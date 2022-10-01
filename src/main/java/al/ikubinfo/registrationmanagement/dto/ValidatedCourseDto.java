package al.ikubinfo.registrationmanagement.dto;


import al.ikubinfo.registrationmanagement.entity.CourseProgressStatus;
import al.ikubinfo.registrationmanagement.validation.UniqueCourseValidation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Valid
@UniqueCourseValidation(
        courseName = "name",
        startDate = "startDate",
        endDate = "endDate",
        message = "Course already exists"
)
public class ValidatedCourseDto extends CourseDto{

    @Override
    @NotNull(message = "not null")
    @NotBlank(message = "Not Blank")
    public String getName() {
        return super.getName();
    }


    @Override
    @NotNull(message = "not null")
    public CourseProgressStatus getStatus() {
        return super.getStatus();
    }

    @Override
    @NotNull(message = "not null")
    public LocalDate getEndDate() {
        return super.getEndDate();
    }



    @Override
    @NotNull(message = "not null")
    public LocalDate getStartDate() {
        return super.getStartDate();
    }
}
