package al.ikubinfo.registrationmanagement.repository;

import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseStatus;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>, JpaSpecificationExecutor<CourseEntity> {

    Optional<CourseEntity> findByCourseNameAndRegistrationStartDateAndStatus(
            String name,
            LocalDate registrationStartDate,
            CourseStatus status);

}