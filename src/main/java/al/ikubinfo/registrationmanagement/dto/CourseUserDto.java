package al.ikubinfo.registrationmanagement.dto;

import al.ikubinfo.registrationmanagement.entity.UserStatusEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseUserDto {
    private Long id;
    private CourseDto course;
    private UserDto user;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private Boolean deleted;
    private UserStatusEnum status;
    private String reference;
    private double priceReduction;
    private double pricePaid;
    private String comment;
}
