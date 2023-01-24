package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseDto;
import al.ikubinfo.registrationmanagement.dto.courseDtos.UpdateCourseDto;
import al.ikubinfo.registrationmanagement.dto.courseDtos.ValidatedCourseDto;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.CourseUserService;
import al.ikubinfo.registrationmanagement.service.UserService;
import al.ikubinfo.registrationmanagement.service.impl.CourseServiceImpl;
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

@RestController
@RequestMapping("courses")
public class CourseController extends ControllerTemplate<CourseDto, CourseCriteria, CourseServiceImpl> {
    @Autowired
    private CourseService courseService;
    public CourseController(CourseServiceImpl service) {
        super(service);
    }

    /**
     * Get all courses. if criteria is applied, courses are filter accordingly
     *
     * @param criteria filter object
     * @return ResponseEntity -> courses filtered list
     */
    @PostMapping("/filter")
    public ResponseEntity<Page<CourseDto>> listCourses(@RequestBody CourseCriteria criteria) {
        return new ResponseEntity<>(courseService.filterCourses(criteria), HttpStatus.OK);
    }

    /**
     * Retrieve course details
     *
     * @param id course id
     * @return ResponseEntity<CourseDto>
     */
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    /**
     * Update course
     *
     * @param course courseDto
     * @return ResponseEntity<CourseDto>
     */
    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@RequestBody UpdateCourseDto course) {
        return new ResponseEntity<>(courseService.updateCourse(course), HttpStatus.OK);
    }

    /**
     * Save new course
     *
     * @param course CourseDto
     * @return ResponseEntity<CourseDto>
     */
    @PostMapping()
    public ResponseEntity<CourseDto> saveCourse(@RequestBody ValidatedCourseDto course) {
        return new ResponseEntity<>(courseService.saveCourse(course),HttpStatus.OK);
    }

    /**
     * Delete course (soft deletion)
     *
     * @param id course id
     * @return ModelAndView
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
