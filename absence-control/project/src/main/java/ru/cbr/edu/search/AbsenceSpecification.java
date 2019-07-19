package ru.cbr.edu.search;

import org.springframework.data.jpa.domain.Specification;
import ru.cbr.edu.model.Absence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AbsenceSpecification implements Specification<Absence> {
    private final SearchCriteria criteria;

    public AbsenceSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Absence> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
            case GREATER_THAN:
                return builder.greaterThanOrEqualTo(root.<Comparable>get(criteria.getKey()), (Comparable) criteria.getValue());
            case LESS_THAN:
                return builder.lessThanOrEqualTo(root.<Comparable>get(criteria.getKey()), (Comparable) criteria.getValue());
            case CONTAINS:
                return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            default:
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
    }
}