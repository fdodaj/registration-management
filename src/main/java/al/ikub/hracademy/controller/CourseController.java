package al.ikub.hracademy.controller;

import al.ikub.hracademy.dto.CourseDto;
import al.ikub.hracademy.entity.CourseEntity;
import al.ikub.hracademy.repository.StudentRepository;
import al.ikub.hracademy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CourseController {
    private static final String ADD_NEW_COURSE_URL = "create_course.html";
    private static final  String REDIRECT_TO_HOMEPAGE_URL = "redirect:/students";

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentRepository repository;

    @GetMapping("/courses")
    public ModelAndView listCourses() {
        ModelAndView mv = new ModelAndView("courses");
        mv.addObject("courses", courseService.getAllCourses());
        return mv;
    }



    @GetMapping("/course/new")
    public ModelAndView goToAddCoursePage(CourseEntity course) {
        ModelAndView mv = new ModelAndView("create_course");
        mv.addObject("course", course);
        return mv;
    }



    @PostMapping("/courses")
    public ModelAndView saveCourse(@ModelAttribute("course") CourseDto course) {
        courseService.saveCourse(course);
        return new ModelAndView("redirect:/courses");
    }

    @GetMapping("/course/{id}")
    public ModelAndView getCourseById(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("course_details");
        mv.addObject("course", courseService.getCourseById(id));
        return mv;
    }


}
