package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import org.springframework.data.domain.Page;

public interface CourseService {

    Page<CourseDto> filterCourses(CourseCriteria criteria);

    CourseDto getCourseById(Long id);

    void saveCourse(CourseDto course);

    CourseDto updateCourse(CourseDto courseDto);

    void deleteCourseById(Long id);


}
