package com.finalproject.controller;

import com.finalproject.entity.User;
import com.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String showAccountPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userService.findByEmail(currentUserName);
        model.addAttribute("user", user);

        return "account";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User theUser) {
        userService.save(theUser);
        return "redirect:/fancy-login";
    }
    @PostMapping("/update")
    public String updateAccount(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/users/account";
    }

}
