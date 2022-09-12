package al.ikub.hracademy.repository;

import al.ikub.hracademy.entity.CourseEntity;
import al.ikub.hracademy.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {


}
