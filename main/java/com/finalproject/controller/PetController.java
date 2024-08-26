package com.finalproject.controller;

import com.finalproject.entity.MedicalFile;
import com.finalproject.entity.Pet;
import com.finalproject.entity.User;
import com.finalproject.service.MedicalFileService;
import com.finalproject.service.PetService;
import com.finalproject.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/pets")
public class PetController {

    private final  PetService petService;
    private final  UserService userService;
    private final  MedicalFileService medicalFileService;

    public PetController(PetService petService, UserService userService, MedicalFileService medicalFileService) {
        this.petService = petService;
        this.userService = userService;
        this.medicalFileService = medicalFileService;
    }

    @GetMapping("")
    public String listMyPets(Model model, Principal principal) {
        String ownerEmail = principal.getName();
        List<Pet> pets = petService.findByOwnerEmail(ownerEmail);

        model.addAttribute("pets", pets);
        return "pets/my-pets";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("petId") Long theId) {

        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        petService.deleteById(theId);

        if(currentUserRole.equals("[ROLE_ADMIN]")) {
            return "redirect:/admin/petList";
        } else {
            return "redirect:/pets";
        }
    }

    @GetMapping("/add")
    public String showFormForAdd(Model theModel) {
        theModel.addAttribute("pet", new Pet());
        return "pets/pet-add";
    }

    @PostMapping("/save")
    public String savePet(@ModelAttribute("pet") Pet pet) {

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(currentUserEmail);

        pet.setOwner(user);
        petService.save(pet);
        return "redirect:/pets";
    }

    @GetMapping("/medicalFiles")
    public String seeMedicalFiles(@RequestParam("petId") Long petId, Model model) {
        List<MedicalFile> medicalFiles = medicalFileService.findByPetId(petId);
        model.addAttribute("medicalFiles", medicalFiles);
        return "pets/medical-files";
    }

    @GetMapping("/update")
    public String showFormForUpdate(@RequestParam("petId") int id, Model model) {
        Pet pet = petService.findById(id);
        model.addAttribute("pet", pet);
        return "pets/pet-update";
    }

    @PostMapping("/update")
    public String updatePet(@ModelAttribute("pet") Pet pet,
                            @RequestParam("ownerId") Long ownerId) {

        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        pet.setOwner(userService.findById(ownerId));
        petService.update(pet);

        if(currentUserRole.equals("[ROLE_PETOWNER]")) {
            return "redirect:/pets";
        } else {
            return "redirect:/admin/ownerPets?ownerId=" + pet.getOwner().getId();
        }
    }

}

