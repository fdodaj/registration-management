package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserListDto;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseUserCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseUserService {

    Page<CourseUserListDto> getCourseUserList(CourseUserCriteria criteria);

    List<CourseUserListDto> getCourseUserListByCourseId(Long courseId);

    CourseUserDto editCourseUser(CourseUserDto courseUserDto);

    CourseUserDto assignUserToCourse(CourseUserDto dto);

    void removeUserFromCourse(Long userId, Long courseId);

    CourseUserDto getCourseUserEntity(Long courseId, Long userId);

}
