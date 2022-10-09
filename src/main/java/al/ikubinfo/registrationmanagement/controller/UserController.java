package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.dto.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.UserDto;
import al.ikubinfo.registrationmanagement.dto.ValidatedUserDto;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCriteria;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private static final String REDIRECT_TO_HOMEPAGE_URL = "redirect:/users";
    private static final String USERS = "users";

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;


    /**
     * Get all users. if criteria is applied, users are filter accordingly
     *
     * @param criteria filter object
     * @return ModelAndView -> users filtered list
     */
    @GetMapping("/users")
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
    @GetMapping("/users/{id}")
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
    @GetMapping("/users/new")
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
    @PostMapping("/users")
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
     * Retrieve user edition view
     *
     * @param id user id
     * @return ModelAndView
     */
    @GetMapping("/users/edit/{id}")
    public ModelAndView updateUserView(@PathVariable("id") Long id) {
        UserDto userDto = userService.getUserById(id);
        ModelAndView mv = new ModelAndView("edit_user");
        mv.addObject("user", userDto);
        return mv;
    }

    /**
     * Update user
     *
     * @param id   user id
     * @param user updated dto
     * @return ModelAndView
     */
    @PostMapping("/users/{id}")
    public ModelAndView updateUser(@PathVariable Long id, @ModelAttribute("user") ValidatedUserDto user) {
        userService.updateUser(user);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    /**
     * Delete user by id (soft deletion)
     *
     * @param id user id
     * @return ModelAndView homepage
     */
    @GetMapping("/users/delete/{id}")
    public ModelAndView deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    /**
     * Assigns user to course
     *
     * @param courseUserDto courseUserDto
     * @return  ModelAndView
     */
    @PostMapping("/users/assign-course")
    public ModelAndView assignUserToCourse(CourseUserDto courseUserDto) {
        ModelAndView modelAndView = new ModelAndView("assign_users_to_course");
        modelAndView.addObject("courseUserDto", courseUserDto);
        courseService.assignUserToCourse(courseUserDto);
        return modelAndView;
    }

    /**
     * Retrieve assign user to course view
     *
     * @param userId userId
     * @param courseId  courseId
     * @param courseUserDto courseUserDto
     *
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
