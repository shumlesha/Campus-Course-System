package com.example.CampusCourseSystem.controller;

import com.example.CampusCourseSystem.dto.*;
import com.example.CampusCourseSystem.dto.validation.OnCreate;
import com.example.CampusCourseSystem.mappers.UserMapper;
import com.example.CampusCourseSystem.models.User;
import com.example.CampusCourseSystem.security.JwtEntity;
import com.example.CampusCourseSystem.services.AuthService;
import com.example.CampusCourseSystem.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account")
@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;

    @Operation(summary = "Register new user")
    @PostMapping("/registration")
    public ResponseEntity<TokenResponse> registerUser(@Validated(OnCreate.class) @RequestBody UserRegisterModel userRegisterModel) {
        User user = userMapper.toEntity(userRegisterModel);
        TokenResponse tokenResponse = authService.register(user);
        log.info("token created");
        return ResponseEntity.ok(tokenResponse);
    }

    @Operation(summary = "Log into the system")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@Validated @RequestBody UserLoginModel userLoginModel) {

        return ResponseEntity.ok(authService.login(userLoginModel));
    }

    @Operation(summary = "Log out system user")
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok().body(new Response());
    }

    @Operation(summary = "Get current user's profile info")
    @GetMapping("/profile")
    public ResponseEntity<UserRegisterModel> getUserProfile(@AuthenticationPrincipal JwtEntity jwtEntity) {
        return ResponseEntity.ok(userMapper.toDto(userService.getById(jwtEntity.getId())));
    }

    @Operation(summary = "Edit current user's profile info")
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
