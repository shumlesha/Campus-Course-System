package com.example.CampusCourseSystem.controller;

import com.example.CampusCourseSystem.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterModel userRegisterModel) {
        return ResponseEntity.ok().body("Регистрация прошла успешно");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginModel userLoginModel) {
        return ResponseEntity.ok().body(new TokenResponse("..."));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok().body(new Response());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        return ResponseEntity.ok().body("...");
    }


    @PutMapping("/profile")
    public ResponseEntity<?> editUserProfile (@RequestBody EditUserProfileModel editUserProfileModel) {
        return ResponseEntity.ok().body("Профиль успешно обновлен");
    }
}
