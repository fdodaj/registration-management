package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserListDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UserDto;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseUserCriteria;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.CourseUserService;
import al.ikubinfo.registrationmanagement.service.UserService;
import al.ikubinfo.registrationmanagement.service.impl.CourseUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("course-user")
public class CourseUserController extends ControllerTemplate<CourseUserDto, CourseUserCriteria, CourseUserServiceImpl> {
    @Autowired
    private CourseUserService courseUserService;
    public CourseUserController(CourseUserServiceImpl service) {
        super(service);
    }

    /**
     * Get all user-courses. if criteria is applied, user-courses are filtered accordingly
     *
     * @param criteria
     * @return ModelAndView
     */
    @PostMapping("/filter")
    public ResponseEntity<Page<CourseUserListDto>> getCourseUserList(@RequestBody CourseUserCriteria criteria) {
        return new ResponseEntity<>(courseUserService.getCourseUserList(criteria), HttpStatus.OK);
    }

    /**
     * Update course-user
     *
     * @param courseUserDto courseUserDto
     * @return ModelAndView
     */
    @PostMapping(path = "/edit/{courseId}/{userId}")
    public ResponseEntity<CourseUserDto> updateCourseUser(@RequestBody CourseUserDto courseUserDto) {
        return new ResponseEntity<>(courseUserService.editCourseUser(courseUserDto), HttpStatus.OK);
    }
    /**
     * Assign user to course
     *
     * @param courseUserDto CourseUserDto
     * @return ModelAndView
     */
    @PostMapping("/assign-user")
    public ResponseEntity<Void> assignUserToCourse(@RequestBody CourseUserDto courseUserDto) {
        courseUserDto.setCreatedDate(LocalDate.now());
        courseUserDto.setModifiedDate(LocalDate.now());
        courseUserService.assignUserToCourse(courseUserDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Delete relationship between user and course
     *
     * @param courseId courseId
     * @param studentId studentId
     * @return ModelAndView
     */
    @GetMapping("/remove/{courseId}/{studentId}")
    public ResponseEntity<Void> removeUserFromCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseUserService.removeUserFromCourse(studentId, courseId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
