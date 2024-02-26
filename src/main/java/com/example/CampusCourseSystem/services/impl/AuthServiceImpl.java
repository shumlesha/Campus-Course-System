package com.example.CampusCourseSystem.services.impl;

import com.example.CampusCourseSystem.dto.TokenResponse;
import com.example.CampusCourseSystem.dto.Account.UserLoginModel;
import com.example.CampusCourseSystem.enums.Role;
import com.example.CampusCourseSystem.models.User;
import com.example.CampusCourseSystem.repository.UserRepository;
import com.example.CampusCourseSystem.security.JwtTokenProvider;
import com.example.CampusCourseSystem.services.AuthService;
import com.example.CampusCourseSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("Пользователь с email " + user.getEmail() + " уже существует");
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new IllegalStateException("Пароль и подтвержденный пароль не совпадают");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.ROLE_USER));
        return userRepository.save(user);
    }
    @Override
    public TokenResponse register(User user) {
        User createdUser = create(user);
        String accessToken = jwtTokenProvider.createAccessToken(createdUser.getId(), createdUser.getEmail(), createdUser.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(createdUser.getId(), createdUser.getEmail());
        TokenResponse tokenResponse = new TokenResponse(user.getId(), user.getEmail(), accessToken, refreshToken);
        return tokenResponse;

    }

    @Override
    public TokenResponse login(UserLoginModel userLoginModel) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginModel.getEmail(), userLoginModel.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.getByEmail(userLoginModel.getEmail());
        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getEmail(), user.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), user.getEmail());
        TokenResponse tokenResponse = new TokenResponse(user.getId(), user.getEmail(), accessToken, refreshToken);
        return tokenResponse;
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
