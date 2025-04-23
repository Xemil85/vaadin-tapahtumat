package org.vaadin.example.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.vaadin.example.AppUser;
import org.vaadin.example.Repositories.UserRepository;

@Component
public class AuthenticatedUser {
    
    private UserRepository userRepository;

    public AuthenticatedUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<AppUser> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        String username = authentication.getName(); // tämä on kirjautuneen käyttäjän käyttäjänimi
        return userRepository.findByUsername(username);
    }
}
