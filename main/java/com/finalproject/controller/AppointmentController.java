package com.finalproject.controller;

import com.finalproject.entity.*;
import com.finalproject.service.AppointmentService;
import com.finalproject.service.DoctorDetailsService;
import com.finalproject.service.MedicalConditionService;
import com.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorDetailsService doctorDetailsService;

    @Autowired
    private MedicalConditionService medicalConditionService;

    @Autowired
    private UserService userService;

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
        return "appointments/new-appointment";
    }

    @PostMapping("/book")
    public String bookAppointment(@RequestParam("petId") Long petId,
                                  @RequestParam("doctorId") Long doctorId,
                                  @RequestParam("date") LocalDate date,
                                  @RequestParam("interval") int interval,
                                  Principal principal) {
        appointmentService.book(petId, doctorId, date, interval, principal.getName());
        return "redirect:/pets/myPets";
    }

    @GetMapping("/petAppointments")
    public String listAppointments(@RequestParam("petId") Long petId,
                                   Model model) {
        List<Appointment> appointments = appointmentService.findAllByPetId(petId);
        model.addAttribute("appointments", appointments);
        return "appointments/pet-appointments";
    }


    @GetMapping("/cancel")
    public String delete(@RequestParam("appointmentId") Long theId) {

        if(appointmentService.findById(theId).getMedicalCondition()!=null) {
            medicalConditionService.deleteById(appointmentService.findById(theId).getMedicalCondition().getId());
        }
        appointmentService.deleteById(theId);
        return "redirect:/appointments/allAppointments";
    }

    @GetMapping("/doctorCancel")
    public String deleteFromDoctor(@RequestParam("appointmentId") Long theId) {

        if(appointmentService.findById(theId).getMedicalCondition()!=null) {
            medicalConditionService.deleteById(appointmentService.findById(theId).getMedicalCondition().getId());
        }
        appointmentService.deleteById(theId);
        return "redirect:/appointments/doctorAppointments";
    }

    @GetMapping("/cancelFromPet")
    public String deleteFromPet(@RequestParam("appointmentId") Long theId) {

        Pet pet = appointmentService.findById(theId).getPet();

        if(appointmentService.findById(theId).getMedicalCondition()!=null) {
            medicalConditionService.deleteById(appointmentService.findById(theId).getMedicalCondition().getId());
        }

        appointmentService.deleteById(theId);
        return "redirect:/appointments/petAppointments?petId=" + pet.getId();
    }

    @GetMapping("/allAppointments")
    public String listAllAppointments(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String doctorEmail = authentication.getName();
        User owner = userService.findByEmail(doctorEmail);
        Long ownerId = owner.getId();

        List<Appointment> appointments = appointmentService.findAllByOwnerId(ownerId);
        model.addAttribute("appointments", appointments);
        return "appointments/all-appointments";
    }

    @GetMapping("/doctorAppointments")
    public String listDoctorAppointments(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String doctorEmail = authentication.getName();
        User doctor = userService.findByEmail(doctorEmail);
        Long doctorId = doctor.getId();

        List<Appointment> appointments = appointmentService.findAllByDoctorId(doctorId);
        model.addAttribute("appointments", appointments);
        return "appointments/doctor-appointments.html";
    }

    @GetMapping("/updateAppointment")
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
        return "appointments/update-appointment";
    }

    @PostMapping("/updateAppointment")
    public String updateAppointment(@RequestParam("appointmentId") Long appointmentId,
                                    @RequestParam("newDay") LocalDate newDay,
                                    @RequestParam("newInterval") int newInterval,
                                    Model model) {

        Appointment appointment = appointmentService.findById(appointmentId);
        appointment.setInterval(newInterval);
        appointment.setDay(newDay);
        appointmentService.update(appointment);

        return "redirect:/appointments/doctorAppointments";
    }

    @GetMapping("/medicalFile")
    public String showMedicalFile(@RequestParam("appointmentId") Long appointmentId, Model model) {
        Appointment appointment = appointmentService.findById(appointmentId);
        if (appointment.getMedicalCondition() == null) {
            MedicalCondition medicalCondition = new MedicalCondition();
            medicalCondition.setAppointment(appointment);
            appointment.setMedicalCondition(medicalCondition);
        }
        model.addAttribute("appointment", appointment);
        return "appointments/medical-file";
    }

    @PostMapping("/medicalFileUpdate")
    public String updateMedicalFile(@ModelAttribute("appointment") Appointment appointment) {

        MedicalCondition medicalCondition = appointment.getMedicalCondition();
        medicalConditionService.update(medicalCondition);
        return "redirect:/appointments/doctorAppointments";
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


