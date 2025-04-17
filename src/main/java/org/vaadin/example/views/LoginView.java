package org.vaadin.example.views;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;

@AnonymousAllowed
@Route("login")
@PageTitle("Kirjaudu")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {
    private final AuthenticationContext authenticationContext;

    public LoginView(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;

        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Kirjaudu sisään");
        i18nForm.setUsername("Käyttäjänimi");
        i18nForm.setPassword("Salasana");
        i18nForm.setSubmit("Kirjaudu sisään");
        i18nForm.setForgotPassword("Unohtuiko salasana?");
        i18n.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle("Väärä käyttäjätunnus tai salasana");
        i18nErrorMessage.setMessage(
                "Tarkista että käyttäjätunnus ja salasana ovat oikein ja yritä uudestaan.");
        i18n.setErrorMessage(i18nErrorMessage);

        LoginForm loginForm = new LoginForm();
        loginForm.setI18n(i18n);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Näytä virhe, jos kirjautuminen epäonnistui
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            setError(true);
        }
    }
}
