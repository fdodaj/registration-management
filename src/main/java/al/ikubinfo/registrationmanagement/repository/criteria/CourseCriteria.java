package al.ikubinfo.registrationmanagement.repository.criteria;

import al.ikubinfo.registrationmanagement.entity.CourseProgressStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class CourseCriteria extends BaseCriteria{

    private String name;
    private CourseProgressStatus status;


}
