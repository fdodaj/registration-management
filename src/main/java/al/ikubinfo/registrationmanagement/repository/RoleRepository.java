package al.ikubinfo.registrationmanagement.repository;
import al.ikubinfo.registrationmanagement.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(String roleName);

}
