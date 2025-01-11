package com.example.webappassignment4.util;

import com.example.webappassignment4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidationUtil {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    private final UserRepository userRepository;

    @Autowired
    public ValidationUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidEmail(String email) {
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
        return emailMatcher.matches();
    }

    public boolean isValidPassword(String password) {
        Matcher passwordMatcher = PASSWORD_PATTERN.matcher(password);
        return passwordMatcher.matches();
    }

    public boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUserName(username);
    }
}
