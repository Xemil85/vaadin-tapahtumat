package org.vaadin.example.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Kirjautuminen")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Kirjaudu sisään");
        i18nForm.setUsername("Käyttäjänimi");
        i18nForm.setPassword("Salasana");
        i18nForm.setSubmit("Kirjaudu sisään");
        i18n.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle("Väärä käyttäjätunnus tai salasana");
        i18nErrorMessage.setMessage(
                "Tarkista että käyttäjätunnus ja salasana ovat oikein ja yritä uudestaan.");
        i18n.setErrorMessage(i18nErrorMessage);

        var login = new LoginForm();
        login.setForgotPasswordButtonVisible(false);
        login.setI18n(i18n);
        login.setAction("login");
        RouterLink forgotPasswordLink = new RouterLink("Unohtuiko salasana?", ResetPasswordView.class);
        forgotPasswordLink.getStyle().set("margin-top", "-1rem");
        forgotPasswordLink.getElement().getThemeList().add("small");

        add(new H1("Tapahtumasovellus"), login, forgotPasswordLink);
    }
}
