package al.ikubinfo.registrationmanagement.validation;

import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Optional;

public class UniqueCourseValidationValidator implements ConstraintValidator<UniqueCourseValidation, Object> {

    @Autowired
    private CourseRepository repository;
    private String courseName;

    private String startDate;

    private String endDate;

    @Override
    public void initialize(UniqueCourseValidation constraintAnnotation) {
        courseName = constraintAnnotation.courseName();
        startDate = constraintAnnotation.startDate();
        endDate = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        String name = (String) beanWrapper.getPropertyValue(courseName);
        LocalDate sDate = (LocalDate) beanWrapper.getPropertyValue(startDate);
        LocalDate eDate = (LocalDate) beanWrapper.getPropertyValue(endDate);

        Optional<CourseEntity> opt = repository.findByNameAndStartDateAndEndDate(name, sDate, eDate);

        return opt.isEmpty();

    }
}
