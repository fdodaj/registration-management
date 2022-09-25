package al.ikubinfo.registrationmanagement.repository.criteria;

import al.ikubinfo.registrationmanagement.entity.StudentStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCriteria extends BaseCriteria {
    private String firstName;
    private String lastName;
    private Long course;
    private StudentStatusEnum status;
}
