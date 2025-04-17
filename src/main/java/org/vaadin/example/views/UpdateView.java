package org.vaadin.example.views;

import org.vaadin.example.*;
import org.vaadin.example.Services.EventService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

@Route("update")
public class UpdateView extends VerticalLayout implements HasUrlParameter<Long> {
    private final EventService eventService;
    private Event event;

    private TextField nameField = new TextField("Tapahtuman nimi");
    private DatePicker dateField = new DatePicker("Päivämäärä");
    private TextField cityField = new TextField("Kaupunki");
    private TextField addressField = new TextField("Osoite");
    private TextField organizerField = new TextField("Järjestäjä");

    private Button saveButton = new Button("Päivitä");
    private Button backButton = new Button("Takaisin");
    private Button deleteButton = new Button("Poista tapahtuma");

    public UpdateView(EventService eventService) {
        this.eventService = eventService;

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
                nameField.setValue(event.getName());
                dateField.setValue(event.getDate());
                cityField.setValue(event.getLocation().getName());
                addressField.setValue(event.getLocation().getAddress());
                organizerField.setValue(event.getOrganizer().getName());
            }
        }
    }
}
