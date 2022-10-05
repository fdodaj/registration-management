package al.ikubinfo.registrationmanagement.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class CourseDateConstraintValidator implements ConstraintValidator<CourseDateValidation, Object> {

    private String startDate;

    private String endDate;


    @Override
    public void initialize(CourseDateValidation constraintAnnotation) {
        startDate = constraintAnnotation.startDate();
        endDate = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        LocalDate sDate = (LocalDate) beanWrapper.getPropertyValue(startDate);
        LocalDate eDate = (LocalDate) beanWrapper.getPropertyValue(endDate);

        assert eDate != null;
        assert sDate != null;
        return sDate.isBefore(eDate);
    }
}
