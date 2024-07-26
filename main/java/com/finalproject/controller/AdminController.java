package com.finalproject.controller;

import com.finalproject.entity.Pet;
import com.finalproject.entity.User;
import com.finalproject.service.PetService;
import com.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @GetMapping("/userList")
    public String listUsers(Model theModel) {
        List<User> theUsers = userService.findAll();
        theModel.addAttribute("users", theUsers);
        return "admin/list-users";
    }

    @GetMapping("/petList")
    public String listPets(Model theModel) {
        List<Pet> thePets = petService.findAll();
        theModel.addAttribute("pets", thePets);
        return "admin/list-pets";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("userId") Long theId) {
        userService.deleteById(theId);
        return "redirect:/admin/userList";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        User user = new User();
        theModel.addAttribute("user", user);
        return "users/user-add";
    }

    @GetMapping("/showUserFormForUpdate")
    public String showUserFormForUpdate(@RequestParam("userId") Long theId, Model theModel) {
        User theUser = userService.findById(theId);
        theModel.addAttribute("user", theUser);
        return "users/user-update";
    }

    @GetMapping("/ownerPets")
    public String showOwnerPets(@RequestParam("ownerId") Long ownerId, Model model) {
        User owner = userService.findById(ownerId);
        List<Pet> pets = petService.findByOwner(owner);
        model.addAttribute("pets", pets);
        return "admin/owner-pets";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/userList";
    }

    @PostMapping("/addUser")
    public String addNewUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/userList";
    }
}
