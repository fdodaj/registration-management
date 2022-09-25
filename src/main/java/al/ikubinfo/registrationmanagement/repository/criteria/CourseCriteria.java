package al.ikubinfo.registrationmanagement.repository.criteria;

import al.ikubinfo.registrationmanagement.entity.CourseProgressStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseCriteria extends BaseCriteria {

    private String name;
    private CourseProgressStatus status;


}
