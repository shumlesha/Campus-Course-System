package com.example.CampusCourseSystem.services.impl;

import com.example.CampusCourseSystem.dto.Account.EditUserProfileModel;
import com.example.CampusCourseSystem.models.User;
import com.example.CampusCourseSystem.repository.UserRepository;
import com.example.CampusCourseSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public void save(User mainTeacher) {
        userRepository.save(mainTeacher);
    }
}
