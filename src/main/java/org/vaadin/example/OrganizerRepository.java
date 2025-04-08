package org.vaadin.example;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    Optional<Organizer> findByName(String name);
}
