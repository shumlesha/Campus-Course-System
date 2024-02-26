package com.example.CampusCourseSystem.services;

import com.example.CampusCourseSystem.dto.CampusCourse.CampusCourseDTO;
import com.example.CampusCourseSystem.dto.CampusGroup.CampusGroupDTO;
import com.example.CampusCourseSystem.dto.CampusGroup.CreateCampusGroupModel;
import com.example.CampusCourseSystem.dto.CampusGroup.EditCampusGroupModel;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    List<CampusGroupDTO> getAllGroups();

    void createGroup(CreateCampusGroupModel createCampusGroupModel);

    void editGroup(UUID id, EditCampusGroupModel editCampusGroupModel);

    void deleteGroup(UUID id);

    List<CampusCourseDTO> getCoursesByGroup(UUID id);
}
