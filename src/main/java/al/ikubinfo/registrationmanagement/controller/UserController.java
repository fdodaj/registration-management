package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.*;
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
import org.springframework.web.bind.annotation.*;
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
     * Retrieve user assign to course view
     *
     * @param id user id
     * @return ModelAndView
     */
    @GetMapping("/users/assign/{id}")
    public ModelAndView assignCourseView(@PathVariable("id") Long id) {
        UserDto userDto = userService.getUserById(id);
        List<CourseDto> courseDto = courseService.getAllUnfilteredCourses();

        ModelAndView mv = new ModelAndView("assign_course_to_user");
        mv.addObject("user", userDto);
        mv.addObject("courses", courseDto);
        return mv;
    }

    /**
     * Update user
     *
     * @param id   user id
     * @param user updated dto
     * @return
     */
    @PostMapping("/users/{id}")
    public ModelAndView updateUser(@PathVariable Long id, @ModelAttribute("user") UpdateUserDto user) {
        UserDto dto = userService.updateUser(user);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    /**
     * Assign user to course
     * When an ACTIVE student is assigned to a course, status is set to REGISTERED
     *
     * @param courseId courseId
     * @param userId   userId
     * @return ModelAndView
     */
//    @PostMapping("/users/{courseId}/{userId}")
//    ModelAndView assignUserToCourse(@PathVariable Long courseId, @PathVariable Long userId) {
//        courseService.addUserToCourse(userId, courseId);
//        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
//    }
    @PostMapping("/users/{courseId}/{userId}")
    ModelAndView assignUserToCourse(@PathVariable Long courseId, @PathVariable Long userId) {
        courseService.assignUserToCourse(userId, courseId);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    /**
     * Delete user by id
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
     * Get users with PAID courses using EntityManager
     * For testing purposes
     *
     * @return ResponseEntity<List < UserDto>>
     */
    @GetMapping("/users/em")
    public ResponseEntity<List<UserDto>> listUsersUsingEntityManager() {
        List<UserDto> list = userService.getUserEM();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    /**
     * delete user from course
     *
     * @param courseId courseId
     * @param userId   userId
     * @return ModelAndView
     */
    @PutMapping("/users/{courseId}/{userId}")
    ResponseEntity<CourseUserDto> removeUserFromCourse(@PathVariable Long courseId, @PathVariable Long userId) {
        return new ResponseEntity<>(courseService.removeUserFromCourse(userId, courseId), HttpStatus.ACCEPTED);
    }
}
