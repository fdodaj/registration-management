package al.ikubinfo.registrationmanagement.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = "deleted = false")
@Table(name = "role")
@Getter
@Setter
public class RoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "name")
    private String name;

    @Size(max = 50)
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "role")
    private List<UserEntity> users = new ArrayList<>();

    public RoleEntity() {
        super();
    }
}
