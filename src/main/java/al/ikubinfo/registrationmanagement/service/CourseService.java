package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;

import java.util.List;

public interface CourseService {

    boolean saveCourse(CourseDto course);

    List<CourseDto> getAllCourses();


    CourseEntity updateCourse(CourseDto courseDtoC);

    CourseEntity getCourseById(Long id);

    void deleteCourseById(Long id);


}
