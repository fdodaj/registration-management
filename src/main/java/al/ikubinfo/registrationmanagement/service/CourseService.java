package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    boolean saveCourse(CourseDto course);

    List<CourseDto> getAllCourses();

    Page<CourseDto> filterCourses(CourseCriteria criteria);


    CourseEntity updateCourse(CourseDto courseDtoC);

    CourseEntity getCourseById(Long id);

    void deleteCourseById(Long id);


}
