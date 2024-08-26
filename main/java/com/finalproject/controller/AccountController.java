package com.finalproject.controller;

import com.finalproject.entity.DoctorDetails;
import com.finalproject.entity.User;
import com.finalproject.service.DoctorDetailsServiceImpl;
import com.finalproject.service.RoleService;
import com.finalproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;
    private final RoleService roleService;
    private final DoctorDetailsServiceImpl doctorDetailsService;
    public AccountController(UserService userService, RoleService roleService, DoctorDetailsServiceImpl doctorDetailsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.doctorDetailsService = doctorDetailsService;
    }

    @GetMapping("")
    public String showAccountPage(Model model) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByEmail(currentUserEmail);
        model.addAttribute("user", user);

        return "account";
    }

    @PostMapping("/update")
    public String updateAccount(@ModelAttribute("user") User theUser,
                                @RequestParam("userId") long userId,
                                @RequestParam(value = "newSpecialization", required = false) String newSpecialization) {

        String role=roleService.findRoleNameByUserId(userId);
        if (role.equals("ROLE_DOCTOR")) {
            doctorDetailsService.updateDoctorSpecialization(userId, newSpecialization);
        }
        userService.update(theUser);
        return "redirect:/account";
    }

}
