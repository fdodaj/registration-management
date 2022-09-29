package al.ikubinfo.registrationmanagement.service.impl;


import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.dto.UserDto;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseValidationService {
    @Autowired
    private CourseRepository courseRepository;

    public String validateCourseInvalidDates(CourseDto courseDto){
        String message = "";
        if (courseDto.getEndDate().isBefore(courseDto.getStartDate())) {
            message = "Please enter correct dates";
        }

        return message;
    }

    public String validateCourseAlreadyExist(CourseDto courseDto){
        String message = "";

        if (courseRepository.getByNameAndStartDateAndEndDate(courseDto.getName(), courseDto.getStartDate(), courseDto.getEndDate()) != null){
            message= "Course already exists";
        }
        return message;
    }
}
