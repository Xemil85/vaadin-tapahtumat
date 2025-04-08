package org.vaadin.example.views;

import org.vaadin.example.Event;
import org.vaadin.example.EventService;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route("")
public class MainView extends VerticalLayout {

    private final EventService eventService;
    private final Grid<Event> eventGrid = new Grid<>();

    public MainView(EventService eventService) {
        this.eventService = eventService;

        H1 title = new H1("Tapahtumat");

        eventGrid.asSingleSelect().addValueChangeListener(event -> {
            Event selected = event.getValue();
            if (selected != null) {
                getUI().ifPresent(ui -> ui.navigate("update/" + selected.getId()));
            }
        });        
        
        eventGrid.addColumn(Event::getName).setHeader("Tapahtuman nimi");
        eventGrid.addColumn(Event::getDate).setHeader("Päivämäärä");
        eventGrid.addColumn(event -> event.getLocation().getName()).setHeader("Paikka");
        eventGrid.addColumn(event -> event.getLocation().getAddress()).setHeader("Osoite");
        eventGrid.addColumn(event -> event.getOrganizer().getName()).setHeader("Järjestäjä");

        eventGrid.setItems(eventService.findAllEvents());

        RouterLink addEventLink = new RouterLink("Lisää uusi tapahtuma", EventView.class);
        add(title, eventGrid, addEventLink);
    }
}
