package al.ikubinfo.registrationmanagement.entity;

import al.ikubinfo.registrationmanagement.dto.CourseStatus;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Where(clause = "deleted = false")
@Table(name = "course")
public class CourseEntity extends BaseEntity {

    @Size(max = 50)
    @Column(name = "course_name")
    private String courseName;

    @Column(name = "price")
    private double price;

    @Size(max = 50)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourseStatus status;

    @Column(name = "course_start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseStartDate;

    @Column(name = "course_end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate courseEndDate;

    @Column(name = "registration_start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationStartDate;


    @Column(name = "registration_end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate registrationEndDate;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseUserEntity> courseUsers = new ArrayList<>();

    public CourseEntity() {
        super();
    }
}


