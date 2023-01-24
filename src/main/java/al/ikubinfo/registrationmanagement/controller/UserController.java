package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.authDtos.PasswordDto;
import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.NewUserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UpdateUserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.ValidatedUserDto;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCriteria;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.CourseUserService;
import al.ikubinfo.registrationmanagement.service.impl.UserServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController extends ControllerTemplate<UserDto, UserCriteria, UserServiceImpl> {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CourseUserService courseUserService;

    public UserController(UserServiceImpl service) {
        super(service);
    }


    /**
     * Get all users. if criteria is applied, users are filter accordingly
     *
     * @param criteria filter object
     * @return ModelAndView -> users filtered list
     */
    @PostMapping("/filter")
    public ResponseEntity<Page<UserDto>> listUsers(@RequestBody UserCriteria criteria) {
            return new ResponseEntity<>(userService.filterUsers(criteria), HttpStatus.OK);
    }

    /**
     * Retrieve user details
     *
     * @param id user id
     * @return ModelAndView with users details
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    /**
     * Save user
     *
     * @param user user dto
     * @return ModelAndView
     */
    @PostMapping()
    public ResponseEntity<UserDto> saveUser(@RequestBody NewUserDto user) {
       return new ResponseEntity<>(userService.saveUser(user), HttpStatus.ACCEPTED);
    }

    /**
     * Assigns user to course
     *
     * @param courseUserDto courseUserDto
     * @return ModelAndView
     */
    @PostMapping("/assign-course")
    public ResponseEntity<CourseUserDto> assignUserToCourse(@RequestBody CourseUserDto courseUserDto) {
        return new ResponseEntity<>( courseUserService.assignUserToCourse(courseUserDto), HttpStatus.OK);
    }


    /**
     * Update user
     *
     * @param user updated dto
     * @return ModelAndView
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserDto user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }


    /**
     * Delete user by id (soft deletion)
     *
     * @param id user id
     * @return ModelAndView homepage
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Change user password
     *
     * @param passwordDto old password, new password
     * @return ModelAndView
     */
    @PostMapping("/changePassword")
    public ResponseEntity<UserDto> changePassword(@RequestBody PasswordDto passwordDto) {
        return new ResponseEntity<>(userService.changePassword(passwordDto), HttpStatus.OK);
    }
}
