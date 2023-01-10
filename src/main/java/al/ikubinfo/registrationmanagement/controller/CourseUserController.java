package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.CourseUserListDto;
import al.ikubinfo.registrationmanagement.dto.UserDto;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseUserCriteria;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.CourseUserService;
import al.ikubinfo.registrationmanagement.service.UserService;
import al.ikubinfo.registrationmanagement.service.export.CourseUserExports;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import java.time.LocalDate;

@Controller
public class CourseUserController {
    private static final String REDIRECT_TO_HOMEPAGE_URL = "redirect:/courses";
    private static final String REDIRECT_TO_ALL_URL = "redirect:/all";
    private static final String COURSES = "courses";
    private static final String COURSEUSER = "courseUser";

    @Autowired
    private CourseUserService courseUserService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseUserExports courseUserExports;

    @GetMapping("/all")
    public ModelAndView getCourseUserList(@Valid CourseUserCriteria criteria) {
        Page<CourseUserListDto> userCourseList = courseUserService.getCourseUserList(criteria);
        ModelAndView mv = new ModelAndView("user_course_list");
        mv.addObject("UserCourseList", userCourseList);
        return mv;
    }

    @GetMapping(path = "{courseId}/{userId}")
    public ResponseEntity<CourseUserDto> getCourseUser(@PathVariable Long courseId, @PathVariable Long userId) {
        return ResponseEntity.ok(courseUserService.getCourseUserEntity(courseId, userId));
    }

    @GetMapping("/all/{courseId}/{userId}")
    public ModelAndView updateCourseUserView(@Valid @PathVariable("courseId")Long courseId, @PathVariable("userId") Long userId) {
        CourseUserDto courseUserDto = courseUserService.getCourseUserEntity(courseId, userId);
        ModelAndView mv = new ModelAndView("edit_user_course");
        mv.addObject(COURSEUSER, courseUserDto);
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

    @GetMapping("/course/assign/{userId}")
    public ModelAndView assignCourseView(@PathVariable("userId") Long userId, CourseUserDto courseUserDto) {
        ModelAndView mv = new ModelAndView("assign_course_to_user");
        UserDto user = userService.getUserById(userId);
        courseUserDto.setUserId(user.getId());
        mv.addObject("courseUserDto", courseUserDto);
        mv.addObject(COURSES, courseService.filterCourses(new CourseCriteria()));
        return mv;
    }

    @PostMapping("/courses/assign-user")
    public ModelAndView assignUserToCourse(CourseUserDto courseUserDto) {
        ModelAndView modelAndView = new ModelAndView(REDIRECT_TO_ALL_URL);
        modelAndView.addObject("courseUserDto", courseUserDto);
        courseUserDto.setCreatedDate(LocalDate.now());
        courseUserDto.setModifiedDate(LocalDate.now());
        courseUserService.assignUserToCourse(courseUserDto);
        return modelAndView;
    }

    @GetMapping("/users/{courseId}/{studentId}")
    ModelAndView removeUserFromCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        ModelAndView mv = new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
        courseUserService.removeUserFromCourse(studentId, courseId);
        return mv;
    }

    @GetMapping(value = "course-user/exportToExcel")
    public ResponseEntity<Resource> exportToExcel(CourseUserCriteria criteria) {
        ByteArrayResource resource;
        HttpHeaders headers = new HttpHeaders();
        String fileName = RandomStringUtils.randomAlphanumeric(17).toUpperCase();


        resource = new ByteArrayResource(courseUserExports.createCourseUserExcel(criteria));
        headers.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + ".xlsx\"");
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @GetMapping(value = "course-user/exportToCvs")
    public ResponseEntity<Resource> exportToCvs(CourseUserCriteria criteria) {
        ByteArrayResource resource;
        HttpHeaders headers = new HttpHeaders();
        String fileName = RandomStringUtils.randomAlphanumeric(17).toUpperCase();

        resource = new ByteArrayResource(courseUserExports.createCourseUserCvs(criteria));
        headers.setContentType(new MediaType("text", "csv"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + ".csv\"");
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @GetMapping(value = "course-user/exportToPdf")
    public ResponseEntity<Resource> exportToPdf( CourseUserCriteria criteria) {
        ByteArrayResource resource;
        HttpHeaders headers = new HttpHeaders();
        String fileName = RandomStringUtils.randomAlphanumeric(17).toUpperCase();

        resource = new ByteArrayResource(courseUserExports.createCourseUserPdf(criteria));
        headers.setContentType(MediaType.APPLICATION_PDF);

        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + ".pdf\"");
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
