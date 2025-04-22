package org.vaadin.example.views;

import org.vaadin.example.Services.UserService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {
    
    public RegisterView(UserService userService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        
        TextField username = new TextField("Käyttäjänimi");
        PasswordField password = new PasswordField("Salasana");
        Button registerBtn = new Button("Rekisteröidy");

        Label status = new Label();

        registerBtn.addClickListener(e -> {
            boolean success = userService.register(username.getValue(), password.getValue());
            if (success) {
                status.setText("Rekisteröinti onnistui!");
            } else {
                status.setText("Käyttäjänimi on jo käytössä.");
            }
        });

        add(username, password, registerBtn, status);
    }
}
