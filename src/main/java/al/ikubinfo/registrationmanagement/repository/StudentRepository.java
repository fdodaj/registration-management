package al.ikubinfo.registrationmanagement.repository;

import al.ikubinfo.registrationmanagement.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long>, JpaSpecificationExecutor<StudentEntity> {

//    List<StudentEntity> getAllByCourseId(Long id);
}
