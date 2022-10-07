package al.ikubinfo.registrationmanagement.repository;

import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseUserRepository extends JpaRepository<CourseUserEntity, Long> {

    CourseUserEntity getByUserIdAndCourseId(Long courseId, Long  userId);
}
