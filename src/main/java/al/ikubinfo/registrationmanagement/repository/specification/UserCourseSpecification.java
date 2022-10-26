package al.ikubinfo.registrationmanagement.repository.specification;


import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCourseCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserCourseSpecification  extends CourseUserSpecificationBuilder<CourseUserEntity, UserCourseCriteria> {

    @Override
    public Specification<CourseUserEntity> filter(UserCourseCriteria criteria) {
        Specification<CourseUserEntity> specification = Specification.where(null);

        if (criteria.getStatus() != null && !criteria.getStatus().getDisplayValue().isEmpty()) {
            specification = specification.and(equalsSpecification("status", criteria.getStatus().getDisplayValue()));
        }

        if (criteria.getCourseName() != null && !criteria.getCourseName().isEmpty()) {
            specification = specification.and(likeUpperSpecification("courseName", criteria.getCourseName()));
        }

        return specification;
    }
}
