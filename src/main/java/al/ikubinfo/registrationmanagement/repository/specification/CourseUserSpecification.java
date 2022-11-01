package al.ikubinfo.registrationmanagement.repository.specification;


import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseUserCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CourseUserSpecification extends SpecificationBuilder<CourseUserEntity, CourseUserCriteria> {

    @Override
    public Specification<CourseUserEntity> filter(CourseUserCriteria criteria) {

        Specification<CourseUserEntity> specification = Specification.where(null);

        if (criteria.getStatus() != null && !criteria.getStatus().getDisplayValue().isEmpty()) {
            specification = specification.and(equalsSpecification("status", criteria.getStatus()));
        }

        if (criteria.getCourseName() != null && !criteria.getCourseName().isEmpty()) {
            specification = specification.and((root, query, builder) ->
                    builder.like(builder.upper(root.get("course").get("courseName")), wrapLikeQuery(criteria.getCourseName())));
        }
        if (criteria.getFirstName() != null && !criteria.getFirstName().isEmpty()) {
            specification = specification.and((root, query, builder) ->
                    builder.like(builder.upper(root.get("user").get("firstName")), wrapLikeQuery(criteria.getFirstName())));
        }

        if (criteria.getCourseStartDateFrom() != null && criteria.getCourseStartDateTo() != null) {
            specification = specification.and((root, query, builder) ->
                    builder.between(root.get("course").get("courseStartDate"), criteria.getCourseStartDateFrom(),
                            criteria.getCourseStartDateTo()));
        } else if (criteria.getCourseStartDateFrom() != null) {
            specification = specification.and((root, query, builder) ->
                    builder.greaterThanOrEqualTo(root.get("course").get("courseStartDate"), criteria.getCourseStartDateFrom()));
        } else if (criteria.getCourseStartDateTo() != null) {
            specification = specification.and((root, query, builder) ->
                    builder.lessThanOrEqualTo(root.get("course").get("courseEndDate"), criteria.getCourseStartDateTo()));
        }

        return specification;
    }
}
