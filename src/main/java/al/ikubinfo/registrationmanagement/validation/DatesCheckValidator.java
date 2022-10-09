package al.ikubinfo.registrationmanagement.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DatesCheckValidator implements ConstraintValidator<DatesCheck, Object> {

    private String firstDate;

    private String secondDate;


    @Override
    public void initialize(DatesCheck constraintAnnotation) {
        firstDate = constraintAnnotation.first();
        secondDate = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext ctx) {

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        LocalDate first = beanWrapper.isReadableProperty(firstDate) ? (LocalDate) beanWrapper.getPropertyValue(firstDate) : null;
        LocalDate second = beanWrapper.isReadableProperty(secondDate) ? (LocalDate) beanWrapper.getPropertyValue(secondDate) : null;

        boolean valid = true;
        if (first != null && second != null) {
            valid = first.isBefore(second);
        }
        return valid;

    }


}
