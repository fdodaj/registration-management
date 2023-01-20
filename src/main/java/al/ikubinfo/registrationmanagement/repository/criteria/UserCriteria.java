package al.ikubinfo.registrationmanagement.repository.criteria;

import al.ikubinfo.registrationmanagement.dto.userDtos.UserStatusEnum;
import lombok.Data;
@Data
public class UserCriteria extends BaseCriteria {
    private String firstName;
    private String lastName;
    private Long course;
    private UserStatusEnum status;
}
