package com.finalproject.controller;

import com.finalproject.entity.Appointment;
import com.finalproject.entity.MedicalCondition;
import com.finalproject.entity.Pet;
import com.finalproject.entity.User;
import com.finalproject.service.AppointmentService;
import com.finalproject.service.MedicalConditionService;
import com.finalproject.service.PetService;
import com.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @Autowired
    private MedicalConditionService medicalConditionService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/myPets")
    public String listMyPets(Model model, Principal principal) {
        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail);
        List<Pet> pets = petService.findByOwner(user);

        model.addAttribute("pets", pets);
        return "pets/my-pets";
    }

    @GetMapping("/showUpdatePet")
    public String showFormForUpdate(@RequestParam("petId") long petId, Model model) {
        Pet pet = petService.findById(petId);
        model.addAttribute("pet", pet);
        return "pet-update";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("petId") Long theId) {
        petService.deleteById(theId);
        return "redirect:/pets/myPets";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Pet pet = new Pet();
        theModel.addAttribute("pet", pet);
        return "pets/pet-add";
    }

    @GetMapping("/update")
    public String showFormForUpdate(@RequestParam("petId") int id, Model model) {
        Pet pet = petService.findById(id);
        model.addAttribute("pet", pet);
        return "pets/pet-update";
    }

    @PostMapping("/save")
    public String savePet(@ModelAttribute("pet") Pet pet) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userService.findByEmail(currentUserName);
        pet.setOwner(user);
        petService.save(pet);
        return "redirect:/pets/myPets";
    }

    @GetMapping("/medicalFiles")
    public String seeMedicalFiles(@RequestParam("petId") Long petId, Model model) {
        List<MedicalCondition> medicalConditions = medicalConditionService.findByPetId(petId);
        model.addAttribute("medicalFiles", medicalConditions);
        return "pets/medical-files";
    }

    @PostMapping("/update")
    public String updatePet(@ModelAttribute("pet") Pet pet) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userService.findByEmail(currentUserName);
        pet.setOwner(user);
        petService.update(pet);
        return "redirect:/pets/myPets";
    }

}

