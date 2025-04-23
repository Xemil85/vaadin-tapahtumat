package org.vaadin.example;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @ManyToOne
    private AppUser createdBy;
     
    public Event(String name, LocalDate date, Location location, Organizer organizer) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.organizer = organizer;
    }

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public AppUser getCreatedBy() { 
        return createdBy; 
    }

    public void setCreatedBy(AppUser createdBy) { 
        this.createdBy = createdBy; 
    }
    
}
