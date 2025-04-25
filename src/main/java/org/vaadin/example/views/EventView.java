package org.vaadin.example.views;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.Classes.AppUser;
import org.vaadin.example.Services.EventService;
import org.vaadin.example.security.AuthenticatedUser;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import jakarta.annotation.security.PermitAll;

@Route("add")
@PermitAll
@SpringComponent
@UIScope
public class EventView extends VerticalLayout {
    private final EventService eventService;
    private final AuthenticatedUser authenticatedUser;

    private final TextField nameField = new TextField("Tapahtuman nimi");
    private final DatePicker dateField = new DatePicker("Päivämäärä");
    private final TextField locationField = new TextField("Kaupunki");
    private final TextField addressField = new TextField("Osoite");
    private final TextField organizerField = new TextField("Järjestäjä (nimi)");
    private final Button saveButton = new Button("Tallenna tapahtuma");

    @Autowired
    public EventView(EventService eventService, AuthenticatedUser authenticatedUser) {
        this.eventService = eventService;
        this.authenticatedUser = authenticatedUser;

        setSizeFull(); // täyttää koko näytön
        setDefaultHorizontalComponentAlignment(Alignment.CENTER); // keskittää lomakkeen vaakasuunnassa
        getStyle().set("justify-content", "center"); // keskittää lomakkeen pystysuunnassa

        H2 title = new H2("Lisää uusi tapahtuma");

        Button backButton = new Button("Takaisin etusivulle", e ->
            getUI().ifPresent(ui -> ui.navigate(""))
        );

        configureForm();

        add(title, nameField, dateField, locationField, addressField, organizerField, new HorizontalLayout(saveButton, backButton));
    }

    private void configureForm() {
        saveButton.addClickListener(e -> {
            Optional<AppUser> currentUser = authenticatedUser.get();
            if (fieldsAreValid() && currentUser.isPresent()) {
                eventService.saveEvent(
                        nameField.getValue(),
                        dateField.getValue(),
                        locationField.getValue(),
                        addressField.getValue(),
                        organizerField.getValue(),
                        currentUser.get()
                );
                Notification.show("Tapahtuma lisätty!");
                clearForm();
            } else {
                Notification.show("Täytä kaikki kentät!");
            }
        });

        HorizontalLayout formLayout = new HorizontalLayout(nameField, dateField, locationField, addressField, organizerField, saveButton);
        add(formLayout);
    }

    private void clearForm() {
        nameField.clear();
        dateField.clear();
        locationField.clear();
        addressField.clear();
        organizerField.clear();
    }

    private boolean fieldsAreValid() {
        return !nameField.isEmpty() &&
               dateField.getValue() != null &&
               !locationField.isEmpty() &&
               !addressField.isEmpty() &&
               !organizerField.isEmpty();
    }
}
