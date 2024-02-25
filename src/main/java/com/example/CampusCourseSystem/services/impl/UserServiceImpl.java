package com.example.CampusCourseSystem.services.impl;

import com.example.CampusCourseSystem.dto.EditUserProfileModel;
import com.example.CampusCourseSystem.dto.TokenResponse;
import com.example.CampusCourseSystem.dto.UserLoginModel;
import com.example.CampusCourseSystem.enums.Role;
import com.example.CampusCourseSystem.models.User;
import com.example.CampusCourseSystem.repository.UserRepository;
import com.example.CampusCourseSystem.security.JwtTokenProvider;
import com.example.CampusCourseSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getById(UUID userId) {
        return userRepository.getById(userId);
    }

    @Override
    public User getByEmail(String email) {

        return userRepository.getByEmail(email);
    }

    @Override
    public void editUserProfile(UUID userId, EditUserProfileModel editUserProfileModel) {
        User user = getById(userId);
        if (user != null) {
            user.setFullName(editUserProfileModel.getFullName());
            user.setBirthDate(editUserProfileModel.getBirthDate());
            userRepository.save(user);
        }
    }
}
