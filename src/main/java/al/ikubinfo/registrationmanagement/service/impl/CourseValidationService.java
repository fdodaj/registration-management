package al.ikubinfo.registrationmanagement.service.impl;


import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class CourseValidationService {
    public String validateCourse(CourseDto courseDto){
        String message = "";
        if (courseDto.getEndDate().isBefore(courseDto.getStartDate())) {
            message = "Please enter correct dates";
        }

        return message;
    }

}
