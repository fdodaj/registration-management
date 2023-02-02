package al.ikubinfo.registrationmanagement.entity;

import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@Where(clause = "deleted = false")
@Table(name = "course")
public class CourseEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "course_name")
    private String courseName;

    @Column(name = "price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourseStatus status;

    @Column(name = "course_start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseStartDate;

    @Column(name = "course_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseEndDate;

    @Column(name = "registration_start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationStartDate;

    @Column(name = "registration_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationEndDate;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseUserEntity> courseUsers = new ArrayList<>();

    public CourseEntity() {
        super();
    }
}


