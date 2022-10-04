package al.ikubinfo.registrationmanagement.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;


    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDate createdDate;


    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDate modifiedDate;


    @Column(name = "deleted")
    private boolean deleted;


}
