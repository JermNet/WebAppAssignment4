package com.example.webappassignment4.controller;

import com.example.webappassignment4.model.User;
import com.example.webappassignment4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.servlet.http.HttpSession;

// This class uses the jakarta servlet to create and delete sessions for the user. Using this way compared to spring security is much more simple to set up,
// but requires slightly more work to manage
@Controller
public class SigninController {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SigninController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/signin")
    public String signin(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {

        // If the user is signed in, seen to error page
        if (session.getAttribute("user") != null) {
            model.addAttribute("error", "You are already signed in!");
            return "failure";
        }

        // Get the user from the db, since we get the username here, we don't have to check in the if statement
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Store user in the session
            session.setAttribute("user", user);
            // Go to the success page
            return "redirect:/signinsuccess";
        } else {
            model.addAttribute("error", "Invalid username or password!");
            // Another failure check
            return "failure";
        }
    }

    // Similar to sign in, but in reverse
    @GetMapping("/signout")
    public String signout(HttpSession session, Model model) {
        if (session == null || session.getAttribute("user") == null) {
            model.addAttribute("error", "You are not signed in!");
            return "failure";
        }
        // Invalidate the session and return to the proper page
        session.invalidate();
        return "redirect:/signoutsuccess";
    }

    @GetMapping("/signin")
    public String signinPage() {return "signin";}

    @GetMapping("/signinsuccess")
    public String signinSuccessPage() {return "signinsuccess";}

    @GetMapping("/signoutsuccess")
    public String signoutSuccessPage() {return "signoutsuccess";}
}