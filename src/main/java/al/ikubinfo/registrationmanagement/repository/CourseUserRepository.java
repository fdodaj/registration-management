package al.ikubinfo.registrationmanagement.repository;

import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUserEntity, CourseUserId>, JpaSpecificationExecutor<CourseUserEntity> {

    CourseUserEntity findByIdCourseIdAndIdUserId(Long courseId, Long userId);

    List<CourseUserEntity> getByIdCourseId(Long courseId);

    List<CourseUserEntity> getCourseUserEntitiesByCourseId(Long courseId);

    List<CourseUserEntity> getCourseUserEntitiesByCourseCourseName(String courseName);


}
