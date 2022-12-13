package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.*;
import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseUserCriteria;
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

    Page<CourseUserListDto> getCourseUserList(CourseUserCriteria criteria);

    CourseUserDto editCourseUser(CourseUserDto courseUserDto);




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
     * Assign user to course
     *
     * @param dto CourseUserDto
     * @return CourseUserDto
     */
    CourseUserDto assignUserToCourse(CourseUserDto dto);

    CourseUserDto updateCourseUser(CourseUserDto dto);

    /**
     * Remove user from course
     *
     * @param userId   user id
     * @param courseId course ic
     */
    void removeUserFromCourse(Long userId, Long courseId);

    /**
     * Retrieve course details
     *
     * @param courseId course id
     * @return List<CourseUserDto>
     */
    List<SimplifiedCourseUserDto> getAllStudentsByCourseId(Long courseId);


    List<CourseUserListDto> getCourseUserList();

    CourseUserDto getCourseUserEntity(Long courseId, Long userId);

}
