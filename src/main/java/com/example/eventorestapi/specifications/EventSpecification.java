package com.example.eventorestapi.specifications;

import com.example.eventorestapi.models.Event;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecification implements Specification<Event> {
    private final String propertyName;
    private final Object filterValue;

    public EventSpecification(String propertyName, Object filterValue) {
        this.propertyName = propertyName;
        this.filterValue = filterValue;
    }

    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (filterValue != null) {
            return builder.equal(root.get(propertyName), filterValue);
        }
        return null;
    }
}
