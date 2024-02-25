package com.example.CampusCourseSystem.services;

import com.example.CampusCourseSystem.dto.CampusCourseDTO;
import com.example.CampusCourseSystem.dto.CampusGroupDTO;
import com.example.CampusCourseSystem.dto.CreateCampusGroupModel;
import com.example.CampusCourseSystem.dto.EditCampusGroupModel;
import com.example.CampusCourseSystem.models.CampusGroup;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    List<CampusGroupDTO> getAllGroups();

    void createGroup(CreateCampusGroupModel createCampusGroupModel);

    void editGroup(UUID id, EditCampusGroupModel editCampusGroupModel);

    void deleteGroup(UUID id);

    List<CampusCourseDTO> getCoursesByGroup(UUID id);
}
