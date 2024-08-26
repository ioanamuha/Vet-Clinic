package com.finalproject.controller;

import com.finalproject.entity.User;
import com.finalproject.service.DoctorDetailsService;
import com.finalproject.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final DoctorDetailsService doctorDetailsService;
    public HomeController(UserService userService, DoctorDetailsService doctorDetailsService) {
        this.userService = userService;
        this.doctorDetailsService = doctorDetailsService;
    }

    @GetMapping("/home")
    public String showHome(Model model) {
        String doctorEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        List<User> doctors = userService.findAllDoctors();
        model.addAttribute("doctors", doctors);

        String specialization = doctorDetailsService.findSpecializationByUserEmail(doctorEmail);
        model.addAttribute("specialization", specialization);

        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
