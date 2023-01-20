package al.ikubinfo.registrationmanagement.dto.courseDtos;

import al.ikubinfo.registrationmanagement.validation.DatesCheck;
import al.ikubinfo.registrationmanagement.validation.DatesChecks;
import al.ikubinfo.registrationmanagement.validation.UniqueCourseValidation;
import javax.validation.Valid;
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
public class ValidatedCourseDto extends CourseDto {

}
