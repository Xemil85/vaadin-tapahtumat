package org.vaadin.example.views;

import org.vaadin.example.Services.UserService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("reset-password")
@AnonymousAllowed
public class ResetPasswordView extends VerticalLayout {
    
    private final UserService userService;

    public ResetPasswordView(UserService userService) {
        this.userService = userService;

        TextField usernameField = new TextField("Käyttäjänimi");
        PasswordField newPasswordField = new PasswordField("Uusi salasana");
        Button resetButton = new Button("Vaihda salasana", e -> {
            if (userService.resetPassword(usernameField.getValue(), newPasswordField.getValue())) {
                Notification.show("Salasana vaihdettu onnistuneesti!");
                getUI().ifPresent(ui -> ui.navigate("login"));
            } else {
                Notification.show("Käyttäjää ei löytynyt.");
            }
        });

        add(new H3("Salasanan vaihto"), usernameField, newPasswordField, resetButton);
        setAlignItems(Alignment.CENTER);
    }
}
