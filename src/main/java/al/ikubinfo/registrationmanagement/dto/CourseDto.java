package al.ikubinfo.registrationmanagement.dto;

import al.ikubinfo.registrationmanagement.entity.CourseProgressStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class CourseDto {

    @NullConstraint
    private Long id;

    @NullConstraint
    private String name;

    @NullConstraint
    private CourseProgressStatus status;

    @NullConstraint
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NullConstraint
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NullConstraint
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @NullConstraint
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

    private List<StudentDto> students;
}
