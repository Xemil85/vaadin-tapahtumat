package org.vaadin.example.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.Classes.Organizer;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    Optional<Organizer> findByName(String name);
}
