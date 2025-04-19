package org.vaadin.example.views;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

    public MainLayout() {
        H1 title = new H1("Tapahtumasovellus");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0")
                .set("padding-left", "var(--lumo-space-l)");

        HorizontalLayout navigation = new HorizontalLayout();
        RouterLink mainViewLink = new RouterLink("Etusivu", MainView.class);
        navigation.add(mainViewLink);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth != null && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken);

        if (isLoggedIn) {
            RouterLink addEventLink = new RouterLink("Lisää tapahtuma", EventView.class);
            Anchor logout = new Anchor("/logout", "Kirjaudu ulos");
            navigation.add(addEventLink, logout);
        } else {
            Anchor login = new Anchor("/login", "Kirjaudu");
            Anchor register = new Anchor("/register", "Luo uusi käyttäjä");
            navigation.add(login, register);
        }

        HorizontalLayout header = new HorizontalLayout(title, navigation);
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);
        header.setPadding(true);

        addToNavbar(header);
    }
}
