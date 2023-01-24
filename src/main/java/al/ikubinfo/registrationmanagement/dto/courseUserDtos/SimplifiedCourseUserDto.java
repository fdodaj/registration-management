package al.ikubinfo.registrationmanagement.dto.courseUserDtos;

import al.ikubinfo.registrationmanagement.dto.BaseDto;
import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UserStatusEnum;
import lombok.Data;
@Data
public class SimplifiedCourseUserDto{
    private Long userId;
    private UserDto userDto;
    private CourseDto courseDto;
    private UserStatusEnum status;
}
