package al.ikubinfo.registrationmanagement.entity;

import al.ikubinfo.registrationmanagement.dto.userDtos.UserStatusEnum;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Data
@Entity
@Table(name = "course_user")
@Where(clause = "deleted = false")
public class CourseUserEntity extends BaseEntity {

    @EmbeddedId
    private CourseUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    private CourseEntity course;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private UserEntity user;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDate createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatusEnum status;

    @Size(max = 100)
    @Column(name = "reference")
    private String reference;

    @Column(name = "price_reduction")
    private Double priceReduction;

    @Column(name = "price_paid")
    private Double pricePaid;

    @Size(max = 500)
    @Column(name = "comment")
    private String comment;

    @Column(name = "deleted")
    private boolean deleted;
    public CourseUserEntity() {
        super();
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
