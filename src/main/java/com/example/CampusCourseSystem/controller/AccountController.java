package com.example.CampusCourseSystem.controller;

import com.example.CampusCourseSystem.dto.*;
import com.example.CampusCourseSystem.dto.validation.OnCreate;
import com.example.CampusCourseSystem.mappers.UserMapper;
import com.example.CampusCourseSystem.models.User;
import com.example.CampusCourseSystem.security.JwtEntity;
import com.example.CampusCourseSystem.services.AuthService;
import com.example.CampusCourseSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;
    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Validated(OnCreate.class) @RequestBody UserRegisterModel userRegisterModel) {
        User user = userMapper.toEntity(userRegisterModel);
        TokenResponse tokenResponse = authService.register(user);
        log.info("token created");
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Validated @RequestBody UserLoginModel userLoginModel) {

        return ResponseEntity.ok(authService.login(userLoginModel));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok().body(new Response());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal JwtEntity jwtEntity) {
        return ResponseEntity.ok(userMapper.toDto(userService.getById(jwtEntity.getId())));
    }


    @PutMapping("/profile")
    public ResponseEntity<?> editUserProfile (@AuthenticationPrincipal JwtEntity jwtEntity, @RequestBody EditUserProfileModel editUserProfileModel) {

        userService.editUserProfile(jwtEntity.getId(), editUserProfileModel);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }


}
