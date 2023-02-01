package al.ikubinfo.registrationmanagement.repository;

import al.ikubinfo.registrationmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends BaseJpaRepository<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

}
