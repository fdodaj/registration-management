package al.ikub.hracademy.repository;

import al.ikub.hracademy.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    List<StudentEntity> getAllByCourseId(Long id);
}
