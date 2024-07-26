package com.finalproject.controller;

import com.finalproject.entity.DoctorDetails;
import com.finalproject.entity.User;
import com.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String showHome(Model model) {

        // Get the logged-in user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        // Fetch user details from the database
        User user = userService.findByEmail(currentUserName);
        model.addAttribute("user", user);

        // Check if the user has the role of doctor and fetch specialization if they do
        if (user.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_DOCTOR"))) {
            DoctorDetails doctorDetails = userService.findDoctorDetailsByUserId(user.getId());
            if (doctorDetails != null) {
                model.addAttribute("specializare", doctorDetails.getSpecializare());
            }
        }

        return "home";
    }


    @GetMapping("about")
    public String about() {
        return "about";
    }

}
