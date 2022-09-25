package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CourseController {
    private static final String REDIRECT_TO_HOMEPAGE_URL = "redirect:/courses";

    @Autowired
    private CourseService courseService;


    /**
     * Get all courses. if criteria is applied, courses are filter accordingly
     *
     * @param criteria filter object
     * @return ModelAndView -> courses filtered list
     */
    @GetMapping("/courses")
    public ModelAndView listCourses(CourseCriteria criteria) {
        Page<CourseDto> courseDtos = courseService.filterCourses(criteria);
        ModelAndView mv = new ModelAndView("courses");
        mv.addObject("courses", courseDtos);
        return mv;
    }

    /**
     * Retrieve student details
     *
     * @param id course id
     * @return ModelAndView with course details
     */
    @GetMapping("/course/{id}")
    public ModelAndView getCourseById(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("course_details");
        mv.addObject("course", courseService.getCourseById(id));
        return mv;
    }

    /**
     * Retrieve form of course creation
     *
     * @param course courseDto
     * @return ModelAndView
     */
    @GetMapping("/course/new")
    public ModelAndView retrieveNewCourseView(CourseDto course) {
        ModelAndView mv = new ModelAndView("create_course");
        mv.addObject("course", course);
        return mv;
    }


    /**
     * Save new course
     *
     * @param course CourseDto
     * @return ModelAndView
     */
    @PostMapping("/courses")
    public ModelAndView saveCourse(@ModelAttribute("course") CourseDto course) {
        courseService.saveCourse(course);
        return new ModelAndView("redirect:/courses");
    }

    /**
     * Retrieve course edition view
     *
     * @param id course id
     * @return ModelAndView
     */
    @GetMapping("/courses/edit/{id}")
    public ModelAndView updateCourseView(@PathVariable("id") Long id) {
        CourseDto courseDto = courseService.getCourseById(id);
        ModelAndView mv = new ModelAndView("edit_course");
        mv.addObject("course", courseDto);
        return mv;
    }

    /**
     * Update course
     *
     * @param id        course id
     * @param courseDto courseDto
     * @return ModelAndView
     */
    @PostMapping("/course/{id}")
    public ModelAndView updateCourse(@PathVariable Long id, @ModelAttribute("course") CourseDto courseDto) {
        courseService.updateCourse(courseDto);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    /**
     * Delete cpourse
     *
     * @param id course id
     * @return ModelAndView
     */
    @GetMapping("/courses/delete/{id}")
    public ModelAndView deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    @PutMapping("addd/{courseId}/{studentId}")
    ResponseEntity<Void> addStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.addStudentToCourse(studentId, courseId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
