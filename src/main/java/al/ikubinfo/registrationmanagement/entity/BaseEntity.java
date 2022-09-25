package al.ikubinfo.registrationmanagement.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @NotNull
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDate createdDate;

    @NotNull
    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @NotNull
    @Column(name = "deleted")
    private boolean deleted;


}
