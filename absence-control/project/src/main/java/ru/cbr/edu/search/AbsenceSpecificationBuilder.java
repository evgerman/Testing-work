package ru.cbr.edu.search;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import ru.cbr.edu.model.Absence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AbsenceSpecificationBuilder {

    private final List<SearchCriteria> params;

    public AbsenceSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public AbsenceSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, SearchOperation.getByName(operation), value));
        return this;
    }

    public Specification<Absence> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Absence>> specs = params.stream().map(AbsenceSpecification::new).collect(Collectors.toList());

        Specification<Absence> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }

    @Override
    public String toString() {
        return "AbsenceSpecificationBuilder{" +
                "params=" + params +
                '}';
    }
}