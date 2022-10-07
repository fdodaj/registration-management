package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.dto.CourseUserDto;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    Page<CourseDto> filterCourses(CourseCriteria criteria);

    CourseDto getCourseById(Long id);

    List<CourseDto> getAllUnfilteredCourses();

    void saveCourse(CourseDto course);

    CourseDto updateCourse(CourseDto courseDto);

    void deleteCourseById(Long id);

    CourseUserDto assignUserToCourse(Long userId,  Long courseId);

    CourseUserDto removeUserFromCourse(Long userId,  Long courseId);



}
