package com.example.CampusCourseSystem.services;

import com.example.CampusCourseSystem.dto.TokenResponse;
import com.example.CampusCourseSystem.dto.UserLoginModel;
import com.example.CampusCourseSystem.models.User;

public interface AuthService {
    TokenResponse register(User user);

    TokenResponse login(UserLoginModel userLoginModel);

    TokenResponse refreshToken(String refreshToken);
}
