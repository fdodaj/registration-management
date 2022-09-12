package al.ikub.hracademy.service;

import al.ikub.hracademy.dto.CourseDto;
import al.ikub.hracademy.entity.CourseEntity;
import al.ikub.hracademy.entity.StudentEntity;

import java.util.List;

public interface CourseService {

    boolean saveCourse(CourseDto course);

    List<CourseEntity> getAllCourses();


    CourseEntity getCourseById(Long id);




}
