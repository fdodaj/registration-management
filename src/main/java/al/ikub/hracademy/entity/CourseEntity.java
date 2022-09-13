package al.ikub.hracademy.entity;

import al.ikub.hracademy.dto.StudentDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@Table(name = "courses")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;


    @NotNull
    @Column(name = "status")
    private CourseProgressStatus status;

    @NotNull
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;


    @Column(name = "end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;


    @Column(name = "date_added")
    private LocalDate dateAdded;

    @NotNull
    @Column(name = "last_modified")
    private LocalDate lastModified;

    @Nullable
    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "course")
    private List<StudentEntity> students = new ArrayList<>();

}


