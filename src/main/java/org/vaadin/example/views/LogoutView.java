package org.vaadin.example.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;

import jakarta.annotation.security.PermitAll;

@Route("logout")
@PermitAll
public class LogoutView extends Main {
    
    public LogoutView(AuthenticationContext authenticationContext) {
        add(new Button("Kirjaudu ulos", event -> authenticationContext.logout()));
    }
}
