package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.authDtos.PasswordDto;
import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.ValidatedUserDto;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCriteria;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.CourseUserService;
import al.ikubinfo.registrationmanagement.service.impl.UserServiceImpl;
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
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController extends ControllerTemplate<UserDto, UserCriteria, UserServiceImpl> {
    private static final String REDIRECT_TO_HOMEPAGE_URL = "redirect:/user/all";
    private static final String USERS = "users";

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CourseService courseService;


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
    @GetMapping("/all")
    public ModelAndView listUsers(@Valid UserCriteria criteria) {
        Page<UserDto> users = userService.filterUsers(criteria);
        ModelAndView mv = new ModelAndView(USERS);
        mv.addObject(USERS, users);
        return mv;
    }

    /**
     * Retrieve user details
     *
     * @param id user id
     * @return ModelAndView with users details
     */
    @GetMapping("/{id}")
    public ModelAndView getUserById(@Valid @PathVariable Long id) {
        ModelAndView mv = new ModelAndView("user_details");
        mv.addObject("user", userService.getUserById(id));
        return mv;
    }

    /**
     * Retrieve form of user creation
     *
     * @param user user dto
     * @return ModelAndView
     */
    @GetMapping("/new")
    public ModelAndView retrieveNewUserView(@Valid UserDto user) {
        ModelAndView mv = new ModelAndView("create_user");
        mv.addObject("user", user);
        return mv;
    }

    /**
     * Save user
     *
     * @param user user dto
     * @return ModelAndView
     */
    @PostMapping("/save")
    public ModelAndView saveUser(@Valid @ModelAttribute("user") ValidatedUserDto user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("create_user");
            mv.addObject("user", user);
            return mv;
        }
        userService.saveUser(user);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    /**
     * Assigns user to course
     *
     * @param courseUserDto courseUserDto
     * @return ModelAndView
     */
    @PostMapping("/assign-course")
    public ModelAndView assignUserToCourse(@Valid @ModelAttribute("course") CourseUserDto courseUserDto) {
        ModelAndView modelAndView = new ModelAndView("assign_users_to_course");
        modelAndView.addObject("courseUserDto", courseUserDto);
        courseUserService.assignUserToCourse(courseUserDto);
        return new ModelAndView("redirect:/course-user/all");
    }

    /**
     * Retrieve user edition view
     *
     * @param id user id
     * @return ModelAndView
     */
    @GetMapping("/edit/{id}")
    public ModelAndView updateUserView(@PathVariable("id") Long id) {
        UserDto userDto = userService.getUserById(id);
        ModelAndView mv = new ModelAndView("edit_user");
        mv.addObject("user", userDto);
        return mv;
    }

    /**
     * Update user
     *
     * @param user updated dto
     * @return ModelAndView
     */
    @PostMapping("/{id}")
    public ModelAndView updateUser(@Valid @ModelAttribute("user") ValidatedUserDto user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("edit_user");
            mv.addObject("user", user);
            return mv;
        }
        userService.updateUser(user);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }


    /**
     * Delete user by id (soft deletion)
     *
     * @param id user id
     * @return ModelAndView homepage
     */
    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }


    /**
     * Retrieve assign user to course view
     *
     * @param userId        userId
     * @param courseId      courseId
     * @param courseUserDto courseUserDto
     * @return ModelAndView
     */
    @GetMapping("/course/assign/{userId}/{courseId}")
    public ModelAndView assignCourseView(@PathVariable("userId") Long userId,
                                         @PathVariable("courseId") Long courseId, CourseUserDto courseUserDto) {
        ModelAndView mv = new ModelAndView("assign_users_to_course");
        UserDto user = userService.getUserById(userId);
        CourseDto course = courseService.getCourseById(courseId);
        courseUserDto.setUserId(user.getId());
        courseUserDto.setCourseId(course.getId());
        mv.addObject("courseUserDto", courseUserDto);
        mv.addObject("user", user);
        mv.addObject("course", course);
        return mv;
    }

    @GetMapping("/change_password")
    public ModelAndView getChangePasswordView(PasswordDto passwordDto) {
        ModelAndView mv = new ModelAndView("change_password");
        mv.addObject("passwordDto", passwordDto);
        return mv;
    }

    @PostMapping("/changePassword")
    public ModelAndView changePassword(PasswordDto passwordDto) {
        ModelAndView mv = new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
        userService.changePassword(passwordDto);
        mv.addObject("passwordData", passwordDto);
        return mv;
    }

    /**
     * Get users with PAID courses using EntityManager
     * For testing purposes
     *
     * @return ResponseEntity<List < UserDto>>
     */
    @GetMapping("/users/em")
    public ResponseEntity<List<UserDto>> listUsersUsingEntityManager() {
        List<UserDto> list = userService.getUsersEM();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
