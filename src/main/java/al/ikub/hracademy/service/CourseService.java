package al.ikub.hracademy.service;

import al.ikub.hracademy.dto.CourseDto;
import al.ikub.hracademy.entity.CourseEntity;

import java.util.List;

public interface CourseService {

    boolean saveCourse(CourseDto course);

    List<CourseDto> getAllCourses();


    CourseEntity updateCourse(CourseDto courseDtoC);

    CourseEntity getCourseById(Long id);

    void deleteCourseById(Long id);




}
