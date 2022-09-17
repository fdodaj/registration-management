package al.ikub.hracademy.dto;

import al.ikub.hracademy.entity.CourseProgressStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private CourseProgressStatus status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private LocalDate dateAdded;
    private LocalDate lastModified;
    private Boolean deleted;
//    private List<StudentEntity> students;
}
