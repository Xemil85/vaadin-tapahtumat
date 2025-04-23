package org.vaadin.example.views;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;

@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

    public MainLayout(AuthenticationContext authenticationContext) {
        H1 title = new H1("Tapahtumasovellus");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0")
                .set("padding-left", "var(--lumo-space-l)");

        HorizontalLayout navigation = new HorizontalLayout();
        Button mainViewLink = new Button("Etusivu", e -> UI.getCurrent().navigate(""));
        navigation.add(mainViewLink);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth != null && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken);

        if (isLoggedIn) {
            Button addEventLink = new Button("Lisää tapahtuma", e -> UI.getCurrent().navigate("add"));
            Button logout = new Button("Kirjaudu ulos", e -> authenticationContext.logout());
            navigation.add(addEventLink, logout);
        } else {
            Button login = new Button("Kirjaudu", e -> UI.getCurrent().navigate("login"));
            Button register = new Button("Luo uusi käyttäjä", e -> UI.getCurrent().navigate("register"));
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
