package al.ikubinfo.registrationmanagement.validation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
public @interface DatesChecks {

    /**
     * Value.
     *
     * @return the dates check[]
     */
    DatesCheck[] value();

}
