package al.ikubinfo.registrationmanagement.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "course_user")
@Where(clause = "deleted = false")
public class CourseUserEntity {

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
