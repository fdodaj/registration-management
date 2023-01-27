package al.ikubinfo.registrationmanagement.dto.courseDtos;

import al.ikubinfo.registrationmanagement.dto.BaseDto;
import lombok.Data;
import java.time.LocalDate;
@Data
public class SimplifiedCourseDto extends BaseDto {
    private String courseName;
    private Double price;
    private CourseStatus status;
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;
    private LocalDate registrationStartDate;
    private LocalDate registrationEndDate;

}
