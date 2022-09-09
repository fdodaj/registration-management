package al.ikub.hracademy.entity;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Where(clause = "deleted = false")
@NoArgsConstructor
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StudentStatusEnum status;


    @Column(name = "reference")
    private String reference;


    @Column(name = "price_reduction")
    private double priceReduction;

    @NotNull
    @Column(name = "price_paid")
    private double pricePaid;


    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "date_added")
    private LocalDateTime dateAdded;

    @NotNull
    @Column(name = "last_modified")
    private LocalDate last_modified;

    @NotNull
    @Column(name = "deleted")
    private Boolean deleted ;



}
