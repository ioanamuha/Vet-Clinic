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

    private final UserService userService;
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showFormForRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute("user") User theUser, Model model) {
        if(userService.findByEmail(theUser.getEmail()) != null) {
            model.addAttribute("exception", true);
            return "register";
        } else {
            userService.save(theUser);
            return "redirect:/login";
        }
    }

}
