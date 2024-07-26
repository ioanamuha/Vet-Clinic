package com.finalproject.controller;

import com.finalproject.entity.Appointment;
import com.finalproject.entity.User;
import com.finalproject.service.AppointmentService;
import com.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @GetMapping("/appointments")
    public String listAppointments(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userService.findByEmail(currentUserName);

        List<Appointment> appointments;
        if (user.hasRole("ROLE_PETOWNER")) {
            appointments = appointmentService.findByOwnerId(user.getId());
        } else if (user.hasRole("ROLE_DOCTOR")) {
            appointments = appointmentService.findByDoctorId(user.getId());
        } else {
            appointments = appointmentService.findAll();
        }

        model.addAttribute("appointments", appointments);
        return "appointments/list-appointments"; // Adjust the view name as needed
    }

    @GetMapping("/new")
    public String newAppointment(Model model) {
        return "appointments/new-appointment";
    }

    @GetMapping("/view")
    public String viewAppointment(Model model) {
        return "appointments/view-appointment";
    }
}
