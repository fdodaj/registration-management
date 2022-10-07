package al.ikubinfo.registrationmanagement.repository.criteria;

import al.ikubinfo.registrationmanagement.entity.CourseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseCriteria extends BaseCriteria {

    private String name;
    private CourseStatus status;


}
