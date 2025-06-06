package org.vaadin.example.views;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.vaadin.example.Classes.AppUser;
import org.vaadin.example.Classes.Event;
import org.vaadin.example.Services.EventService;
import org.vaadin.example.security.AuthenticatedUser;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

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
@Route(value = "")
@AnonymousAllowed
public class MainView extends VerticalLayout implements AfterNavigationObserver {

    private final EventService eventService;
    private final Grid<Event> eventGrid = new Grid<>();
    private final AuthenticatedUser authenticatedUser;

    TextField nameFilter = new TextField("Suodata nimellä");
    DatePicker DateFilter = new DatePicker("Suodata päivämäärällä");
    TextField locationFilter = new TextField("Suodata paikalla");
    TextField addressFilter = new TextField("Suodata osoitteella");
    TextField organizerFilter = new TextField("Suodata järjestäjällä");

    public MainView(EventService eventService, AuthenticatedUser authenticatedUser) {
        this.eventService = eventService;
        this.authenticatedUser = authenticatedUser;

        setDefaultHorizontalComponentAlignment(Alignment.CENTER); // keskittää lomakkeen vaakasuunnassa
        getStyle().set("justify-content", "center"); // keskittää lomakkeen pystysuunnassa

        H1 title = new H1("Tapahtumat");

        add(title);

        configureGrid();
        add(eventGrid);

        eventGrid.asSingleSelect().addValueChangeListener(event -> {
            Event selected = event.getValue();
            
            if (selected != null) {
                Optional<AppUser> currentUser = authenticatedUser.get();
                if (currentUser.isPresent()) {
                    AppUser u = currentUser.get();
                    boolean isOwner = selected.getCreatedBy().getId().equals(u.getId());
                    boolean isAdmin = "ADMIN".equals(u.getRole());
                    if (isOwner || isAdmin) {
                        getUI().ifPresent(ui -> ui.navigate("update/" + selected.getId()));
                        return;
                    }
                } else {
                    Notification.show("Et voi muokata muiden käyttäjien tapahtumia");
                }
            }
        });        

        updateGrid();

        H4 footerText = new H4("© 2025 Tapahtumasovellus");

        add(footerText);
    }

    private void configureGrid() {
        eventGrid.setWidthFull();

        Column<Event> nameColumn = eventGrid.addColumn(Event::getName).setHeader("Nimi").setKey("name");
        Column<Event> dateColumn = eventGrid.addColumn(Event::getDate).setHeader("Päivämäärä").setKey("date");
        Column<Event> locationColumn = eventGrid.addColumn(e -> e.getLocation().getName()).setHeader("Kaupunki").setKey("location");
        Column<Event> addressColumn = eventGrid.addColumn(e -> e.getLocation().getAddress()).setHeader("Osoite").setKey("address");
        Column<Event> organizerColumn = eventGrid.addColumn(e -> e.getOrganizer().getName()).setHeader("Järjestäjä").setKey("organizer");

        HeaderRow filterRow = eventGrid.appendHeaderRow();

        // Filtterikenttien asetukset
        configureFilter(nameFilter, filterRow.getCell(nameColumn));
        configureFilter(locationFilter, filterRow.getCell(locationColumn));
        configureFilter(addressFilter, filterRow.getCell(addressColumn));
        configureFilter(organizerFilter, filterRow.getCell(organizerColumn));

        DateFilter.setPlaceholder("Päivämäärä");
        DateFilter.setClearButtonVisible(true);
        DateFilter.addValueChangeListener(e -> updateGrid());
        filterRow.getCell(dateColumn).setComponent(DateFilter);
    }

    private void configureFilter(TextField filter, HeaderRow.HeaderCell cell) {
        filter.setPlaceholder("Suodata...");
        filter.setClearButtonVisible(true);
        filter.setWidthFull();
        filter.addValueChangeListener(e -> updateGrid());
        cell.setComponent(filter);
    }

    public void updateGrid() {
        List<Event> filtered = eventService.findAllEvents().stream()
            .filter(e -> nameFilter.getValue().isEmpty() || e.getName().toLowerCase().contains(nameFilter.getValue().toLowerCase()))
            .filter(e -> DateFilter.isEmpty() || e.getDate().equals(DateFilter.getValue()))
            .filter(e -> locationFilter.getValue().isEmpty() || e.getLocation().getName().toLowerCase().contains(locationFilter.getValue().toLowerCase()))
            .filter(e -> addressFilter.getValue().isEmpty() || e.getLocation().getAddress().toLowerCase().contains(addressFilter.getValue().toLowerCase()))
            .filter(e -> organizerFilter.getValue().isEmpty() || e.getOrganizer().getName().toLowerCase().contains(organizerFilter.getValue().toLowerCase()))
            .collect(Collectors.toList());

        eventGrid.setItems(filtered);
    }

    public void refreshGridFromBackgroundThread() {
        UI ui = UI.getCurrent();
        if (ui != null) {
            new Thread(() -> {
                List<Event> newEvents = eventService.findAllEvents();
                ui.access(() -> {
                    eventGrid.setItems(newEvents);
                    Notification.show("Tapahtumat päivitetty taustalta!");
                });
            }).start();
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        refreshGridFromBackgroundThread();
    }
    
}
