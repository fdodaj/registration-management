package al.ikubinfo.registrationmanagement.dto.courseDtos;

import lombok.Data;
import java.time.LocalDate;
@Data
public class SimplifiedCourseDto {
    private String courseName;
    private Double price;
    private CourseStatus status;
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;
    private LocalDate registrationStartDate;
    private LocalDate registrationEndDate;

}
