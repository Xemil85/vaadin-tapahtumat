package org.vaadin.example.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;

@Layout
public class MainLayout extends AppLayout {

    public MainLayout() {
        H1 title = new H1("Tapahtumasovellus");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0")
                .set("padding-left", "var(--lumo-space-l)");

        RouterLink mainViewLink = new RouterLink("Etusivu", MainView.class);
        RouterLink addEventLink = new RouterLink("Lisää tapahtuma", EventView.class);

        HorizontalLayout navigation = new HorizontalLayout(mainViewLink, addEventLink);

        HorizontalLayout header = new HorizontalLayout(title, navigation);
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);
        header.setPadding(true);

        addToNavbar(header);
    }
}
