package al.ikubinfo.registrationmanagement.repository.criteria;

import al.ikubinfo.registrationmanagement.dto.userDtos.UserStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
@Getter
@Setter
public class CourseUserCriteria extends BaseCriteria {

    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    private String courseName;

    private String firstName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseStartDateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseStartDateTo;

    private Boolean deleted;
}
