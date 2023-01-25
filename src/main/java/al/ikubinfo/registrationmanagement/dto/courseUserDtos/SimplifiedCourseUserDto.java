package al.ikubinfo.registrationmanagement.dto.courseUserDtos;

import al.ikubinfo.registrationmanagement.dto.userDtos.UserStatusEnum;
import lombok.Data;
@Data
public class SimplifiedCourseUserDto{
    private String courseName;
    private  String studentName;
    private UserStatusEnum status;
}
