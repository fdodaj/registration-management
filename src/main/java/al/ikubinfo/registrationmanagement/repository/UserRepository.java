package al.ikubinfo.registrationmanagement.repository;

import al.ikubinfo.registrationmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.net.PortUnreachableException;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    public UserEntity getByEmail(String email);

}
