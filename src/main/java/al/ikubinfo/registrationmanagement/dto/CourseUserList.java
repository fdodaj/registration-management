package al.ikubinfo.registrationmanagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseUserList {
//    private String userFirstName;
//    private String userLastName;
//    private String userEmail;
//    private String userPhoneNumber;
//    private String userReachForm;
//
//    private String courseName;
//    private String coursePrice;
//    private String courseStatus;
//    private LocalDate courseStartDate;
//    private LocalDate courseEndDate;
//    private LocalDate courseRegistrationStartDate;
//    private LocalDate courseRegistrationEndDate;

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
