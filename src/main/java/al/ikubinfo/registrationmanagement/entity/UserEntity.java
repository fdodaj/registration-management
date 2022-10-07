package al.ikubinfo.registrationmanagement.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Where(clause = "deleted = false")
@NoArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity {


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "Last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatusEnum status;

    @Column(name = "reference")
    private String reference;

    @Column(name = "price_reduction")
    private double priceReduction;

    @Column(name = "price_paid")
    private double pricePaid;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_role")
    @JsonIgnore
    private RoleEntity role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseUserEntity> userCourses = new ArrayList<>();


}
