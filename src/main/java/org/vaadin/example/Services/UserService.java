package org.vaadin.example.Services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vaadin.example.Classes.AppUser;
import org.vaadin.example.Repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);
        AppUser newUser = new AppUser();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        userRepository.save(newUser);

        return true;
    }

    public boolean resetPassword(String username, String newPassword) {
        Optional<AppUser> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            AppUser user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }
    
}
