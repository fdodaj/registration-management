package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseDto;
import al.ikubinfo.registrationmanagement.dto.courseDtos.ValidatedCourseDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.SimplifiedCourseUserDto;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    /**
     * Get all courses. if criteria is applied, courses are filter accordingly
     *
     * @param criteria CourseCriteria
     * @return CourseDto
     */
    Page<CourseDto> filterCourses(CourseCriteria criteria);

    /**
     * Retrieve course details
     *
     * @param id course id
     * @return
     */
    CourseDto getCourseById(Long id);

    /**
     * Retrieve user assign to course view
     *
     * @return List<CourseDto>
     */
    List<CourseDto> getAllUnfilteredCourses();

    /**
     * Save course
     *
     * @param course CourseDto
     * @return
     */
    CourseDto saveCourse(ValidatedCourseDto course);

    /**
     * Update course
     *
     * @param courseDto course dto
     * @return CourseDto
     */
    CourseDto updateCourse(CourseDto courseDto);

    /**
     * Delete course
     *
     * @param courseId course id
     */
    void deleteCourseById(Long courseId);


    /**
     * Retrieve course details
     *
     * @param courseId course id
     * @return List<CourseUserDto>
     */
    List<SimplifiedCourseUserDto> getAllStudentsByCourseId(Long courseId);

}
