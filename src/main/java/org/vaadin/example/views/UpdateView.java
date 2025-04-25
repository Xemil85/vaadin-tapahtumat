package org.vaadin.example.views;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.*;
import org.vaadin.example.Classes.AppUser;
import org.vaadin.example.Classes.Event;
import org.vaadin.example.Services.EventService;
import org.vaadin.example.security.AuthenticatedUser;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route("update")
@PermitAll
public class UpdateView extends VerticalLayout implements HasUrlParameter<Long> {
    private final EventService eventService;
    private final AuthenticatedUser authenticatedUser;
    private Event event;

    private TextField nameField = new TextField("Tapahtuman nimi");
    private DatePicker dateField = new DatePicker("Päivämäärä");
    private TextField cityField = new TextField("Kaupunki");
    private TextField addressField = new TextField("Osoite");
    private TextField organizerField = new TextField("Järjestäjä");

    private Button saveButton = new Button("Päivitä");
    private Button backButton = new Button("Takaisin");
    private Button deleteButton = new Button("Poista tapahtuma");

    @Autowired
    public UpdateView(EventService eventService, AuthenticatedUser authenticatedUser) {
        this.eventService = eventService;
        this.authenticatedUser = authenticatedUser;

        setSizeFull(); // täyttää koko näytön
        setDefaultHorizontalComponentAlignment(Alignment.CENTER); // keskittää lomakkeen vaakasuunnassa
        getStyle().set("justify-content", "center"); // keskittää lomakkeen pystysuunnassa

        saveButton.addClickListener(e -> {
            if (event != null) {
                eventService.updateEvent(
                    event.getId(),
                    nameField.getValue(),
                    dateField.getValue(),
                    cityField.getValue(),
                    addressField.getValue(),
                    organizerField.getValue()
                );
                getUI().ifPresent(ui -> ui.navigate(""));
            }
        });

        deleteButton.addClickListener(e -> {
            if (event != null) {
                eventService.deleteEventById(event.getId());
                getUI().ifPresent(ui -> ui.navigate(""));
            }
        });
        
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));
        add(nameField, dateField, cityField, addressField, organizerField,
            new HorizontalLayout(saveButton, deleteButton), backButton);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long eventId) {
        if (eventId != null) {
            this.event = eventService.findEventById(eventId);

            if (event != null) {
                Optional<AppUser> currentUser = authenticatedUser.get();
                if (currentUser.isEmpty()) {
                    Notification.show("Et ole kirjautunut sisään");
                    getUI().ifPresent(ui -> ui.navigate("login"));
                    return;
                }

                AppUser u = currentUser.get();
                boolean isOwner = event.getCreatedBy().getId().equals(u.getId());
                boolean isAdmin = "ADMIN".equals(u.getRole());
                if (!isOwner && !isAdmin) {
                    Notification.show("Et voi muokata muiden käyttäjien tapahtumia");
                    getUI().ifPresent(ui -> ui.navigate(""));
                    return;
                }
                nameField.setValue(event.getName());
                dateField.setValue(event.getDate());
                cityField.setValue(event.getLocation().getName());
                addressField.setValue(event.getLocation().getAddress());
                organizerField.setValue(event.getOrganizer().getName());
            }
        }
    }
}
