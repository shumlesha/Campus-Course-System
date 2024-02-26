package com.example.CampusCourseSystem.services.impl;

import com.example.CampusCourseSystem.dto.CampusCourse.CampusCourseDTO;
import com.example.CampusCourseSystem.dto.CampusGroup.CampusGroupDTO;
import com.example.CampusCourseSystem.dto.CampusGroup.CreateCampusGroupModel;
import com.example.CampusCourseSystem.dto.CampusGroup.EditCampusGroupModel;
import com.example.CampusCourseSystem.exceptions.ResourceNotFoundException;
import com.example.CampusCourseSystem.mappers.CampusCourseMapper;
import com.example.CampusCourseSystem.mappers.CampusGroupMapper;
import com.example.CampusCourseSystem.models.CampusCourse;
import com.example.CampusCourseSystem.models.CampusGroup;
import com.example.CampusCourseSystem.repository.CampusCourseRepository;
import com.example.CampusCourseSystem.repository.CampusGroupRepository;
import com.example.CampusCourseSystem.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final CampusCourseRepository campusCourseRepository;
    private final CampusGroupRepository campusGroupRepository;
    private final CampusGroupMapper campusGroupMapper;
    private final CampusCourseMapper campusCourseMapper;
    @Override
    public List<CampusGroupDTO> getAllGroups() {
        return campusGroupMapper.toDtoList(campusGroupRepository.findAll());
    }

    @Override
    public void createGroup(CreateCampusGroupModel createCampusGroupModel) {
        if (campusGroupRepository.existsByName(createCampusGroupModel.getName())) {
            throw new IllegalStateException("Группа " + createCampusGroupModel.getName() + " уже существует");
        }
        CampusGroup campusGroup = new CampusGroup();
        campusGroup.setName(createCampusGroupModel.getName());
        campusGroupRepository.save(campusGroup);
    }

    @Override
    public void editGroup(UUID id, EditCampusGroupModel editCampusGroupModel) {
        CampusGroup group = campusGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не существет группы с id " + id));
        group.setName(editCampusGroupModel.getName());
        campusGroupRepository.save(group);
    }

    @Override
    public void deleteGroup(UUID id) {
        CampusGroup group = campusGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не существует группы с id " + id));
        campusGroupRepository.delete(group);
    }

    @Override
    public List<CampusCourseDTO> getCoursesByGroup(UUID id) {
        if (!campusGroupRepository.existsById(id)) {
            throw new ResourceNotFoundException("Нет группы с таким id");
        }

        List<CampusCourse> courses = campusCourseRepository.findAllByCampusGroupId(id);
        return courses.stream()
                .map(campusCourseMapper::toDto)
                .collect(Collectors.toList());
    }
}
