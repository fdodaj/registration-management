package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseDto;
import al.ikubinfo.registrationmanagement.dto.courseDtos.ValidatedCourseDto;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.CourseUserService;
import al.ikubinfo.registrationmanagement.service.UserService;
import al.ikubinfo.registrationmanagement.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
@RequestMapping("courses")
public class CourseController extends ControllerTemplate<CourseDto, CourseCriteria, CourseServiceImpl> {
    private static final String REDIRECT_TO_HOMEPAGE_URL = "redirect:/courses";
    private static final String COURSES = "courses";
    private static final String COURSE = "course";

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    @Autowired
    private CourseUserService courseUserService;

    public CourseController(CourseServiceImpl service) {
        super(service);
    }

    /**
     * Get all courses. if criteria is applied, courses are filter accordingly
     *
     * @param criteria filter object
     * @return ModelAndView -> courses filtered list
     */
    @GetMapping()
    public ModelAndView listCourses(@Valid CourseCriteria criteria) {
        Page<CourseDto> courseDtos = courseService.filterCourses(criteria);
        ModelAndView mv = new ModelAndView(COURSES);
        mv.addObject(COURSES, courseDtos);
        return mv;
    }

    /**
     * Retrieve course details
     *
     * @param id course id
     * @return ModelAndView with course details
     */
    @GetMapping("/{id}")
    public ModelAndView getCourseById(@Valid @PathVariable Long id) {
        ModelAndView mv = new ModelAndView("course_details");
        mv.addObject(COURSE, courseService.getCourseById(id));
        mv.addObject("users", courseService.getAllStudentsByCourseId(id));
        mv.addObject("userList", userService.getUnassignedUsers());
        mv.addObject("userCourseList", courseUserService.getCourseUserListByCourseId(id));
        return mv;
    }

    /**
     * Update course
     *
     * @param course courseDto
     * @return ModelAndView
     */
    @PostMapping("/{id}")
    public ModelAndView updateCourse(@Valid @ModelAttribute("course") CourseDto course, BindingResult result, Model model) {
        model.addAttribute(COURSE, course);
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("edit_course");
            mv.addObject(COURSE, course);
            return mv;
        }
        courseService.updateCourse(course);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    /**
     * Save new course
     *
     * @param course CourseDto
     * @return ModelAndView
     */
    @PostMapping()
    public ModelAndView saveCourse(@Valid @ModelAttribute("course") ValidatedCourseDto course, BindingResult result, Model model) {
        model.addAttribute(COURSE, course);
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("create_course");
            mv.addObject(COURSE, course);
            return mv;
        }
        courseService.saveCourse(course);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    /**
     * Delete course (soft deletion)
     *
     * @param id course id
     * @return ModelAndView
     */
    @GetMapping("/delete/{id}")
    public ModelAndView deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    /**
     * Retrieve course edition view
     *
     * @param id course id
     * @return ModelAndView
     */
    @GetMapping("/edit-form/{id}")
    public ModelAndView updateCourseView(@Valid @PathVariable("id") Long id) {
        CourseDto courseDto = courseService.getCourseById(id);
        ModelAndView mv = new ModelAndView("edit_course");
        mv.addObject(COURSE, courseDto);
        return mv;
    }

    /**
     * Retrieve form of course creation
     *
     * @param course courseDto
     * @return ModelAndView
     */
    @GetMapping("/creation-form")
    public ModelAndView retrieveNewCourseView(@Valid CourseDto course, BindingResult result) {
        ModelAndView mv = new ModelAndView("create_course");
        mv.addObject(COURSE, course);
        return mv;
    }
}
