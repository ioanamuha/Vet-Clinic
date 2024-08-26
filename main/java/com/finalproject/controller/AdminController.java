package com.finalproject.controller;

import com.finalproject.entity.*;
import com.finalproject.service.PetService;
import com.finalproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PetService petService;
    public AdminController(UserService userService, PetService petService) {
        this.userService = userService;
        this.petService = petService;
    }

    @GetMapping("/userList")
    public String listUsers(Model theModel) {
        List<User> theUsers = userService.findAll();
        theModel.addAttribute("users", theUsers);
        return "admin/list-users";
    }

    @GetMapping("/delete/user")
    public String deleteUser(@RequestParam("userId") Long theId) {
        userService.deleteById(theId);
        return "redirect:/admin/userList";
    }

    @GetMapping("/update")
    public String showUserFormForUpdate(@RequestParam("userId") Long theId, Model theModel) {
        User theUser = userService.findById(theId);
        theModel.addAttribute("user", theUser);
        return "admin/user-update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/userList";
    }

    @GetMapping("/add/user")
    public String showFormForAdd(Model theModel) {
        User user = new User();
        theModel.addAttribute("user", user);
        return "admin/user-add";
    }

    @PostMapping("/add/user")
    public String addNewUser(@ModelAttribute("user") User user,
                             @RequestParam("roleName") String roleName,
                             Model model) {

        if(userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("exception", true);
            return "admin/user-add";
        } else {
            userService.save(user, roleName);
            return "redirect:/admin/userList";
        }
    }

    @GetMapping("/delete/pet")
    public String deletePet(@RequestParam("petId") Long petId) {

        Long ownerId = userService.findByPetId(petId).getId();

        petService.deleteById(petId);
        return "redirect:/admin/ownerPets?ownerId=" + ownerId;
    }

    @GetMapping("/doctorAppointments")
    public String doctorAppointments(@RequestParam("doctorId") Long doctorId, Model model) {
        User doctor = userService.findById(doctorId);
        List<Appointment> appointments = doctor.getAppointments();
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointments);
        return "admin/doctor-appointments";
    }

    @GetMapping("/ownerPets")
    public String showOwnerPets(@RequestParam("ownerId") Long ownerId, Model model) {
        User owner = userService.findById(ownerId);
        List<Pet> pets = petService.findByOwner(owner);
        model.addAttribute("owner", owner);
        model.addAttribute("pets", pets);
        return "admin/owner-pets";
    }

    @GetMapping("/ownerPets/add")
    public String addOwnerPet(@RequestParam("ownerId") Long ownerId, Model theModel) {

        Pet pet = new Pet();
        theModel.addAttribute("pet", pet);
        theModel.addAttribute("ownerId", ownerId);

        return "admin/owner-pet-add";
    }

    @PostMapping("/ownerPets/add")
    public String saveOwnerPet(@RequestParam("ownerId") Long ownerId,@ModelAttribute("pet") Pet pet) {

        pet.setOwner(userService.findById(ownerId));
        petService.save(pet);
        return "redirect:/admin/ownerPets?ownerId=" + ownerId;
    }

    @GetMapping("/petList")
    public String listPets(Model theModel) {
        List<Pet> thePets = petService.findAll();
        theModel.addAttribute("pets", thePets);
        return "admin/list-pets";
    }

    @GetMapping("/petList/add")
    public String addPet(Model theModel) {
        Pet pet = new Pet();
        User petOwner = new User();
        List<User> possiblePetOwners = userService.findAllPetOwners();

        theModel.addAttribute("possiblePetOwners", possiblePetOwners);
        theModel.addAttribute("petOwner", petOwner);
        theModel.addAttribute("pet", pet);

        return "admin/pet-add";
    }

    @PostMapping("/petList/add")
    public String savePet(@ModelAttribute("pet") Pet pet,
                          @ModelAttribute("petOwner") User petOwner) {
        pet.setOwner(petOwner);
        petService.save(pet);
        return "redirect:/admin/petList";
    }
}
