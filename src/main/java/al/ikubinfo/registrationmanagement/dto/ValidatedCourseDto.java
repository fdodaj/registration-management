package al.ikubinfo.registrationmanagement.dto;


import al.ikubinfo.registrationmanagement.entity.CourseStatus;
import al.ikubinfo.registrationmanagement.validation.CourseDateValidation;
import al.ikubinfo.registrationmanagement.validation.UniqueCourseValidation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Valid
@UniqueCourseValidation(
        courseName = "name",
        startDate = "startDate",
        endDate = "endDate",
        message = "Course already exists!"
)
@CourseDateValidation(
        startDate = "startDate",
        endDate = "endDate",
        message = "Please enter correct dates!"
)
public class ValidatedCourseDto extends CourseDto {

    @Override
    @NotNull(message = "Name cant be empty!")
    public String getCourseName() {
        return super.getCourseName();
    }

    @Override
    @NotNull(message = "status cant be empty!")
    public CourseStatus getStatus() {
        return super.getStatus();
    }

    @Override
    @NotNull(message = "start date cant be empty!")
    public LocalDate getCourseStartDate() {
        return super.getCourseStartDate();
    }

    @Override
    @NotNull(message = "end date cant be empty!")
    public LocalDate getCourseEndDate() {
        return super.getCourseEndDate();
    }


}
