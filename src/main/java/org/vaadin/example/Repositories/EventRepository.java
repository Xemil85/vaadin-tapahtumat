package org.vaadin.example.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    
}
