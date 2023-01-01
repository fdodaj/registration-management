package al.ikubinfo.registrationmanagement.dto;

import al.ikubinfo.registrationmanagement.entity.CourseUserId;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseUserListDto {
    private CourseUserId id;
    private UserDto userDto;
    private CourseDto courseDto;
    private UserStatusEnum status;
    private String reference;
    private String comment;
    private Double pricePaid;
    private Double priceReduction;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
}
