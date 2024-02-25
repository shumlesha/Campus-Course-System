package com.example.CampusCourseSystem.services;


import com.example.CampusCourseSystem.dto.EditUserProfileModel;
import com.example.CampusCourseSystem.dto.TokenResponse;
import com.example.CampusCourseSystem.dto.UserLoginModel;
import com.example.CampusCourseSystem.models.User;

import java.util.UUID;

public interface UserService {

    User getById(UUID userId);

    User getByEmail(String email);

    void editUserProfile(UUID userId, EditUserProfileModel editUserProfileModel);

}
