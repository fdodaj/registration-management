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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("course-user")
public class CourseUserController extends ControllerTemplate<CourseUserDto, CourseUserCriteria, CourseUserServiceImpl> {
    private static final String REDIRECT_TO_HOMEPAGE_URL = "redirect:/course/all";
    private static final String REDIRECT_TO_ALL_URL = "redirect:/course-user/all";
    private static final String COURSES = "courses";
    private static final String COURSEUSER = "courseUser";

    @Autowired
    private CourseUserService courseUserService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    public CourseUserController(CourseUserServiceImpl service) {
        super(service);
    }

    @GetMapping("/all")
    public ModelAndView getCourseUserList(@Valid CourseUserCriteria criteria) {
        Page<CourseUserListDto> userCourseList = courseUserService.getCourseUserList(criteria);
        ModelAndView mv = new ModelAndView("user_course_list");
        mv.addObject("UserCourseList", userCourseList);
        return mv;
    }

    @PostMapping(path = "/edit/{courseId}/{userId}")
    public ModelAndView updateCourseUser(@ModelAttribute CourseUserDto courseUserDto, BindingResult result, Model model) {
        model.addAttribute(COURSEUSER, courseUserDto);
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("edit_user_course");
            mv.addObject(COURSEUSER, courseUserDto);
            mv.addObject("course", courseService.getCourseById(courseUserDto.getCourseId()));
            mv.addObject("user", userService.getUserById(courseUserDto.getUserId()));
            return mv;
        }
        courseUserService.editCourseUser(courseUserDto);
        return new ModelAndView(REDIRECT_TO_ALL_URL);
    }

    @GetMapping("/update/{courseId}/{userId}")
    public ModelAndView updateCourseUserView(@Valid @PathVariable("courseId") Long courseId, @PathVariable("userId") Long userId) {
        CourseUserDto courseUserDto = courseUserService.getCourseUserEntity(courseId, userId);
        ModelAndView mv = new ModelAndView("edit_user_course");
        mv.addObject(COURSEUSER, courseUserDto);
        return mv;
    }


    @GetMapping("/course/assign/{userId}")
    public ModelAndView assignCourseView(@PathVariable("userId") Long userId, CourseUserDto courseUserDto) {
        ModelAndView mv = new ModelAndView("assign_course_to_user");
        UserDto user = userService.getUserById(userId);
        courseUserDto.setUserId(user.getId());
        mv.addObject("courseUserDto", courseUserDto);
        mv.addObject(COURSES, courseService.filterCourses(new CourseCriteria()));
        return mv;
    }

    @PostMapping("/assign-user")
    public ModelAndView assignUserToCourse(CourseUserDto courseUserDto) {
        ModelAndView modelAndView = new ModelAndView(REDIRECT_TO_ALL_URL);
        modelAndView.addObject("courseUserDto", courseUserDto);
        courseUserDto.setCreatedDate(LocalDate.now());
        courseUserDto.setModifiedDate(LocalDate.now());
        courseUserService.assignUserToCourse(courseUserDto);
        return modelAndView;
    }

    @GetMapping("/remove/{courseId}/{studentId}")
    ModelAndView removeUserFromCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        ModelAndView mv = new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
        courseUserService.removeUserFromCourse(studentId, courseId);
        return mv;
    }
}
