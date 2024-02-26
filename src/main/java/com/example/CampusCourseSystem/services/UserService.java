package com.example.CampusCourseSystem.services;


import com.example.CampusCourseSystem.dto.Account.EditUserProfileModel;
import com.example.CampusCourseSystem.models.User;

import java.util.UUID;

public interface UserService {

    User getById(UUID userId);

    User getByEmail(String email);

    void editUserProfile(UUID userId, EditUserProfileModel editUserProfileModel);

    void save(User mainTeacher);
}
