package al.ikubinfo.registrationmanagement.repository;

import al.ikubinfo.registrationmanagement.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    List<StudentEntity> getAllByCourseId(Long id);
}
