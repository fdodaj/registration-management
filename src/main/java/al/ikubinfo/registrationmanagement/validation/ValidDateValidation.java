package al.ikubinfo.registrationmanagement.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = {ValidDateConstraintValidator.class})
@Documented
public @interface ValidDateValidation {

    Class<?>[] groups() default {};

    String message() default "InvalidDate";

    Class<? extends Payload>[] payload() default {};

    String startDate();

    String endDate();

}
