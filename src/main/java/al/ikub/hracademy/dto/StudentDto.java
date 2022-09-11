package al.ikub.hracademy.dto;

import al.ikub.hracademy.entity.StudentStatusEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDto {

    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private StudentStatusEnum status;
    private String reference;
    private double priceReduction;
    private double pricePaid;
    private String comment;
    private LocalDate lastModified;
    private LocalDate dateAdded;
    private Boolean deleted;
}
