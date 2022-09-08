package al.ikub.hracademy.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StudentStatusEnum status;

    @Column(name = "reference")
    private String reference;

    @Column(name = "price_reduction")
    private double priceReduction;

    @Column(name = "price_paid")
    private double pricePaid;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date_added")
    private LocalDateTime dateAdded;

    @Column(name = "last_modified")
    private LocalDateTime last_modified;

    @Column(name = "deleted")
    private Boolean deleted ;



}
