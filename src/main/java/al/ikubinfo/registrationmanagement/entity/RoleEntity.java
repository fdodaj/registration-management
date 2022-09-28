package al.ikubinfo.registrationmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
@Entity
@Where(clause = "deleted=0")
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
}
