package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.AssignCourseToUserDto;
import al.ikubinfo.registrationmanagement.dto.CourseDto;
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

    void addStudentToCourse(Long studentId, Long courseId);

    void deleteStudentFromCourse(Long studentId, Long courseId);


}
