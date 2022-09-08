package al.ikub.hracademy.repository;

import al.ikub.hracademy.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

}
