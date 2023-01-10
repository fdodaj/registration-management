package al.ikubinfo.registrationmanagement.dto;

import lombok.Data;

@Data
public class SimplifiedCourseUserDto {

    private Long userId;

    private UserDto userDto;

    private CourseDto courseDto;

    private UserStatusEnum status;

}
