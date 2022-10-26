package al.ikubinfo.registrationmanagement.repository.criteria;

import al.ikubinfo.registrationmanagement.dto.UserStatusEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class UserCourseCriteria extends BaseCriteria{




    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    private String courseName;

}
