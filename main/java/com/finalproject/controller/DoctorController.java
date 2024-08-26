package com.finalproject.controller;

import com.finalproject.entity.Appointment;
import com.finalproject.entity.DoctorDetails;
import com.finalproject.entity.MedicalFile;
import com.finalproject.entity.User;
import com.finalproject.service.AppointmentService;
import com.finalproject.service.DoctorDetailsService;
import com.finalproject.service.MedicalFileService;
import com.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final AppointmentService appointmentService;
    private final MedicalFileService medicalFileService;
    private final DoctorDetailsService doctorDetailsService;
    private final UserService userService;

    public DoctorController(AppointmentService appointmentService, MedicalFileService medicalFileService, DoctorDetailsService doctorDetailsService, UserService userService) {
        this.appointmentService = appointmentService;
        this.medicalFileService = medicalFileService;
        this.doctorDetailsService = doctorDetailsService;
        this.userService = userService;
    }

    @GetMapping("/cancel")
    public String deleteFromDoctor(@RequestParam("appointmentId") Long theId) {

        Appointment appointment = appointmentService.findById(theId);
        User doctor = userService.findById(appointment.getDoctor().getId());

        if(appointment.getMedicalFile()!=null) {
            medicalFileService.deleteById(appointmentService.findById(theId).getMedicalFile().getId());
        }
        appointmentService.deleteById(theId);

        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if(currentUserRole.equals("[ROLE_ADMIN]")) {
            return "redirect:/admin/doctorAppointments?doctorId=" + doctor.getId();
        } else {
            return "redirect:/doctor/appointments";
        }
    }

    @GetMapping("/appointments")
    public String listDoctorAppointments(Model model) {
        String doctorEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Appointment> appointments = appointmentService.findAllByDoctorEmail(doctorEmail);
        model.addAttribute("appointments", appointments);
        return "doctor/appointments";
    }

    @GetMapping("/update")
    public String updateAppointmentPage(@RequestParam("appointmentId") Long appointmentId,
                                        @RequestParam(value = "date", required = false) LocalDate date,
                                        Model model) {
        Appointment appointment = appointmentService.findById(appointmentId);

        model.addAttribute("selectedDate", date);

        if (date != null) {
            List<Appointment> slots = appointmentService.getAvailableSlots(appointment.getDoctor().getId(), date);
            model.addAttribute("slots", slots);
            model.addAttribute("selectedDate", date);
        }

        model.addAttribute("next30Days", generateNext30Days());
        model.addAttribute("appointment", appointment);
        return "doctor/update-appointment";
    }

    @PostMapping("/update")
    public String updateAppointment(@RequestParam("appointmentId") Long appointmentId,
                                    @RequestParam("newDay") LocalDate newDay,
                                    @RequestParam("newInterval") int newInterval) {

        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        Appointment appointment = appointmentService.findById(appointmentId);
        appointment.setInterval(newInterval);
        appointment.setDay(newDay);
        appointmentService.update(appointment);

        if(currentUserRole.equals("[ROLE_ADMIN]")) {
            return "redirect:/admin/doctorAppointments?doctorId=" + appointment.getDoctor().getId();
        } else {
            return "redirect:/doctor/appointments";
        }
    }

    @GetMapping("/medicalFile")
    public String showMedicalFile(@RequestParam("appointmentId") Long appointmentId, Model model) {
        Appointment appointment = appointmentService.findById(appointmentId);
        if (appointment.getMedicalFile() == null) {
            MedicalFile medicalFile = new MedicalFile();
            medicalFile.setAppointment(appointment);
            appointment.setMedicalFile(medicalFile);
        }
        model.addAttribute("appointment", appointment);
        return "/doctor/medical-file";
    }

    @PostMapping("/medicalFileUpdate")
    public String updateMedicalFile(@ModelAttribute("appointment") Appointment appointment) {

        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        MedicalFile medicalCondition = appointment.getMedicalFile();
        medicalFileService.update(medicalCondition);

        Appointment tempAppointment = appointmentService.findById(appointment.getId());

        if(currentUserRole.equals("[ROLE_ADMIN]")) {
            return "redirect:/admin/doctorAppointments?doctorId=" + tempAppointment.getDoctor().getId();
        } else {
            return "redirect:/doctor/appointments";
        }
    }

    private List<LocalDate> generateNext30Days() {
        LocalDate startDate = LocalDate.now();
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dates.add(startDate.plusDays(i));
        }
        return dates;
    }
}
