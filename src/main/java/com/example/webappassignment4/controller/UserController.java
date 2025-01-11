package com.example.webappassignment4.controller;

import com.example.webappassignment4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "viewdata";
    }
}
