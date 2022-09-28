package al.ikubinfo.registrationmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = "deleted = false")
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "role")
    private List<UserEntity> users = new ArrayList<>();
}
