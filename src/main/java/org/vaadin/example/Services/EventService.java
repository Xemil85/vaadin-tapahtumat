package org.vaadin.example.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vaadin.example.AppUser;
import org.vaadin.example.Event;
import org.vaadin.example.Location;
import org.vaadin.example.Organizer;
import org.vaadin.example.Repositories.EventRepository;
import org.vaadin.example.Repositories.LocationRepository;
import org.vaadin.example.Repositories.OrganizerRepository;
import org.vaadin.example.security.AuthenticatedUser;

@Service
public class EventService {

    private final EventRepository eventRepo;
    private final LocationRepository locationRepo;
    private final OrganizerRepository organizerRepo;
    private AuthenticatedUser authenticatedUser;

    public EventService(EventRepository eventRepo, LocationRepository locationRepo, OrganizerRepository organizerRepo) {
        this.eventRepo = eventRepo;
        this.locationRepo = locationRepo;
        this.organizerRepo = organizerRepo;
    }

    public List<Event> findAllEvents() {
        return eventRepo.findAll();
    }

    public Event saveEvent(String name, LocalDate date, String locationName, String address, String organizerName, AppUser creator) {
        Location location = locationRepo.findByName(locationName)
                .map(existing -> {
                    existing.setAddress(address);
                    return locationRepo.save(existing);
                })
                .orElseGet(() -> locationRepo.save(new Location(locationName, address)));

        Organizer organizer = organizerRepo.findByName(organizerName)
                .orElseGet(() -> organizerRepo.save(new Organizer(organizerName)));

        Event event = new Event(name, date, location, organizer);
        event.setCreatedBy(creator);
        return eventRepo.save(event);
    }

    public Event findEventById(Long id) {
        return eventRepo.findById(id).orElse(null);
    }    

    public Event updateEvent(Long eventId, String name, LocalDate date, String city, String address, String organizerName) {
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new RuntimeException("Tapahtumaa ei löytynyt."));

        event.setName(name);
        event.setDate(date);

        Location location = event.getLocation();
        location.setName(city);
        location.setAddress(address);
        locationRepo.save(location);

        Organizer organizer = event.getOrganizer();
        organizer.setName(organizerName);
        organizerRepo.save(organizer);

        return eventRepo.save(event);
    }

    public void deleteEventById(Long id) {
        eventRepo.deleteById(id);
    }

    public AppUser getCurrentUser() {
        return authenticatedUser.get().orElseThrow();
    }
}
