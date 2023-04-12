package com.example.eventorestapi.specifications;

import com.example.eventorestapi.models.Event;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EventSpecification implements Specification<Event> {
    private final List<Filter> filters = new ArrayList<>();

    public void addFilter(Filter filter) {
        filters.add(filter);
    }
    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate predicate = null;
        for (Filter filter : filters) {
            if (filter.getValue() != null) {
                Predicate newPredicate = switch (filter.getOperator()) {
                    case EQUALS -> builder.equal(root.get(filter.getPropertyName()), filter.getValue());
                    case GREATER_THAN -> builder.greaterThan(root.get(filter.getPropertyName()), (Long) filter.getValue());
                    case LESS_THAN -> builder.lessThan(root.get(filter.getPropertyName()), (Long) filter.getValue());
                    case LIKE -> builder.like(builder.lower(root.get(filter.getPropertyName())), "%" + filter.getValue().toString().toLowerCase() + "%");
                };
                if (predicate != null) {
                    predicate = builder.and(predicate, newPredicate);
                } else {
                    predicate = newPredicate;
                }
            }
        }
        return predicate;
    }

    public List<Filter> getFilters() {
        return filters;
    }
}
