package al.ikubinfo.registrationmanagement.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = {DatesCheckValidator.class})
@Documented
public @interface DatesCheck {

    Class<?>[] groups() default {};

    String message() default "Invalid Dates";

    Class<? extends Payload>[] payload() default {};

    String first();

    String second();

}
