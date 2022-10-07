package al.ikubinfo.registrationmanagement.repository;

import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseUserRepository extends JpaRepository<CourseUserEntity, Long> {

    CourseUserEntity getByUserIdAndCourseId(Long courseId, Long userId);

    List<CourseUserEntity> getAllByCourseId(Long courseId);


}
