package com.example.internintelligenceportfolioapi.controller;

import com.example.internintelligenceportfolioapi.model.output.UserDtoOutput;
import com.example.internintelligenceportfolioapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public UserDtoOutput get() {
        return userService.get();
    }

    @PatchMapping("/update/name")
    public void updateName(@RequestParam("newName") String newName) {
        userService.updateName(newName);
    }

    @PatchMapping("/update/surname")
    public void updateSurname(@RequestParam("newSurname") String newSurname) {
        userService.updateSurname(newSurname);
    }

    @PatchMapping("/update/birthDate")
    public void updateBirthDate(@RequestParam("newBirthDate") LocalDate newBirthDate) {
        userService.updateBirthDate(newBirthDate);
    }

    @DeleteMapping("/delete/birthDate")
    public void deleteBirthDate() {
        userService.deleteBirthDate();
    }

    @PatchMapping("/update/email")
    public void updateEmail(@RequestParam("newEmail") String newEmail) {
        userService.updateEmail(newEmail);
    }

    @PatchMapping("/update/password")
    public void updatePassword(@RequestParam("currentPassword") String currentPassword,
                               @RequestParam("newPassword") String newPassword) {
        userService.updatePassword(currentPassword, newPassword);
    }

    @PostMapping("/add/skill")
    public void addSkill(@RequestParam("skill") String skill) {
        userService.addSkill(skill);
    }

    @DeleteMapping("/delete/skill")
    public void deleteSkill(@RequestParam("skill") String skill) {
        userService.deleteSkill(skill);
    }

    @DeleteMapping("/delete")
    public void delete() {
        userService.delete();
    }
}
