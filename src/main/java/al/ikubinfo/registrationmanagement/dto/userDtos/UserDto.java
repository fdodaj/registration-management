package al.ikubinfo.registrationmanagement.dto.userDtos;

import al.ikubinfo.registrationmanagement.dto.BaseDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.SimplifiedCourseUserDto;
import al.ikubinfo.registrationmanagement.dto.roleDtos.RoleDto;
import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;
@Data
public class UserDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private ReachFormEnum reachForm; // rekomandim, rrjete sociale etj

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

    private boolean isAssigned;

    private RoleDto role;

    private List<SimplifiedCourseUserDto> userCourses;

}
