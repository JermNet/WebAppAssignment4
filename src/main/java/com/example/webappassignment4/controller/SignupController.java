package com.example.webappassignment4.controller;

import com.example.webappassignment4.model.User;
import com.example.webappassignment4.repository.UserRepository;
import com.example.webappassignment4.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// This class is specifically for letting the user sign up, as well as serving the signup page. It uses the validationUtil if accounts can be made, and uses the
// password encoder to hash passwords.
@Controller
public class SignupController {
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public SignupController(UserRepository userRepository, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/signup")
    public String submit(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        if (validationUtil.usernameExists(username) || validationUtil.emailExists(email)) {
            model.addAttribute("error", "That account already exists!");
            return "failure";
        }

        if (!validationUtil.isValidUsername(username)) {
            model.addAttribute("error", "Username cannot be empty!");
            return "failure";
        }

        if (!validationUtil.isValidEmail(email)) {
            model.addAttribute("error", "Not a valid email format!");
            return "failure";
        }

        if (!validationUtil.isValidPassword(password)) {
            model.addAttribute("error", "A password must be at least 8 characters long, have an upper and lower case letter, number, and special character!");
            return "failure";
        }

        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, hashedPassword);
        userRepository.save(user);
        return "redirect:/signupsuccess";
    }
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/signupsuccess")
    public String signupSuccessPage() {
        return "signupsuccess";
    }

    @GetMapping("/failure")
    public String signupFailurePage() {
        return "failure";
    }


}