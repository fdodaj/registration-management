package al.ikubinfo.registrationmanagement.repository;

import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseStatus;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CourseRepository extends BaseJpaRepository<CourseEntity> {

    Optional<CourseEntity> findByCourseNameAndRegistrationStartDateAndStatus(
            String name,
            LocalDate registrationStartDate,
            CourseStatus status);

}