package al.ikubinfo.registrationmanagement.repository.specification;

import al.ikubinfo.registrationmanagement.entity.BaseEntity;
import al.ikubinfo.registrationmanagement.repository.criteria.BaseCriteria;
import org.springframework.data.jpa.domain.Specification;
public abstract class SpecificationBuilder<E extends BaseEntity, C extends BaseCriteria> {
    public abstract Specification<E> filter(C criteria);
    protected <T> Specification<E> equalsSpecification(String fieldName, T value) {
        return (root, query, builder) -> builder.equal(root.get(fieldName), value);
    }
    protected Specification<E> likeUpperSpecification(String fieldName, String value) {
        return (root, query, builder) ->
                builder.like(builder.upper(root.get(fieldName)), wrapLikeQuery(value));
    }
    protected String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }

}
