package com.finalproject.controller;

import com.finalproject.entity.User;
import com.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showFormForRegister(Model model) {
        model.addAttribute("user", new User());
        return "users/user-form";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute("user") User theUser) {
        userService.save(theUser);
        return "redirect:/fancy-login";
    }
}
