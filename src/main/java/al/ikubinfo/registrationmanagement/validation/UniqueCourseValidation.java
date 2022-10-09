package al.ikubinfo.registrationmanagement.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = {UniqueCourseConstraintValidator.class})
@Documented
public @interface UniqueCourseValidation {

    Class<?>[] groups() default {};

    String message() default "Course already exists";

    Class<? extends Payload>[] payload() default {};

    String courseName();

    String registrationStartDate();


}
