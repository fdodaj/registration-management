package al.ikubinfo.registrationmanagement.entity;


import al.ikubinfo.registrationmanagement.dto.ReachFormEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Where(clause = "deleted = false")
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Size(max = 50)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 50)
    @Column(name = "Last_name")
    private String lastName;

    @Size(max = 20)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 50)
    @Column(name = "email")
    private String email;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Size(max = 50)
    @Enumerated(EnumType.STRING)
    @Column(name = "reach_form")
    private ReachFormEnum reachForm;

    @ManyToOne
    @JoinColumn(name = "user_role")
    @JsonIgnore
    private RoleEntity role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseUserEntity> userCourses = new ArrayList<>();


}
