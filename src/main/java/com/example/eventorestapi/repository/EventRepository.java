package com.example.eventorestapi.repository;

import com.example.eventorestapi.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Event findByAuthor(String author);
    boolean existsByAuthor(String author);
}
