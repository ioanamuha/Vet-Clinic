package com.finalproject.controller;

import com.finalproject.entity.*;
import com.finalproject.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorDetailsService doctorDetailsService;
    private final MedicalFileService medicalFileService;
    private final PetService petService;
    public AppointmentController(AppointmentService appointmentService, DoctorDetailsService doctorDetailsService, MedicalFileService medicalFileService, PetService petService) {
        this.appointmentService = appointmentService;
        this.doctorDetailsService = doctorDetailsService;
        this.medicalFileService = medicalFileService;
        this.petService = petService;
    }


    @GetMapping("/new")
    public String showNewAppointmentForm(@RequestParam("petId") Long petId,
                                         @RequestParam(value = "doctorId", required = false) Long doctorId,
                                         @RequestParam(value = "date", required = false) LocalDate date,
                                         @RequestParam(value = "specialization", required = false) String specialization,
                                         Model model) {
        List<String> specializations = doctorDetailsService.findAllSpecializations();
        model.addAttribute("specializations", specializations);
        model.addAttribute("selectedSpecialization", specialization);

        if (specialization != null) {
            List<DoctorDetails> doctors = doctorDetailsService.findDoctorsBySpecialization(specialization);
            model.addAttribute("doctors", doctors);
            model.addAttribute("selectedDoctorId", doctorId);
            model.addAttribute("selectedDate", date);
        }

        if (doctorId != null && date != null) {
            List<Appointment> slots = appointmentService.getAvailableSlots(doctorId, date);
            model.addAttribute("slots", slots);
            model.addAttribute("selectedDate", date);
        }

        model.addAttribute("petId", petId);
        model.addAttribute("next30Days", generateNext30Days());
        return "/pets/appointments/new-appointment";
    }

    @PostMapping("/book")
    public String bookAppointment(@RequestParam("petId") Long petId,
                                  @RequestParam("doctorId") Long doctorId,
                                  @RequestParam("date") LocalDate date,
                                  @RequestParam("interval") int interval,
                                  Principal principal) {
        appointmentService.book(petId, doctorId, date, interval, principal.getName());
        return "redirect:/pets";
    }

    @GetMapping("/petAppointments")
    public String listAppointments(@RequestParam("petId") Long petId,
                                   Model model) {
        List<Appointment> appointments = appointmentService.findAllByPetId(petId);
        model.addAttribute("appointments", appointments);
        return "pets/appointments/pet-appointments";
    }

    @GetMapping("/cancel")
    public String delete(@RequestParam("appointmentId") Long theId) {

        if(medicalFileService.findByAppointmentId(theId)!=null) {
            medicalFileService.deleteById(medicalFileService.findByAppointmentId(theId).getId());
        }
        appointmentService.deleteById(theId);
        return "redirect:/appointments/allAppointments";
    }

    @GetMapping("/cancelFromPet")
    public String deleteFromPet(@RequestParam("appointmentId") Long theId) {

        Long petId = petService.findByAppointmentId(theId).getId();

        if(medicalFileService.findByAppointmentId(theId)!=null) {
            medicalFileService.deleteById(medicalFileService.findByAppointmentId(theId).getId());
        }

        appointmentService.deleteById(theId);
        return "redirect:/appointments/petAppointments?petId=" + petId;
    }

    @GetMapping("/allAppointments")
    public String listAllAppointments(Model model) {

        String ownerEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Appointment> appointments = appointmentService.findAllByOwnerEmail(ownerEmail);
        model.addAttribute("appointments", appointments);
        return "/pets/appointments/all-appointments";
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


