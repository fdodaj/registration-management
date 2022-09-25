package al.ikubinfo.registrationmanagement.repository.specification;

import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CourseSpecification extends SpecificationBuilder<CourseEntity, CourseCriteria> {


    @Override
    public Specification<CourseEntity> filter(CourseCriteria criteria) {
        Specification<CourseEntity> specification = Specification.where(null);

        if (criteria.getName() != null) {
            specification = specification.and(equalsSpecification("name", criteria.getName()));
        }
        if (criteria.getStatus() != null) {
            specification = specification.and(equalsSpecification("status", criteria.getStatus()));
        }

        return specification;
    }
}
