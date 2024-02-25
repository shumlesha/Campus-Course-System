package com.example.CampusCourseSystem.mappers;

import com.example.CampusCourseSystem.dto.CampusGroupDTO;
import com.example.CampusCourseSystem.models.CampusGroup;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CampusGroupMapper {
    CampusGroupDTO toDto(CampusGroup campusGroup);
    CampusGroup toEntity(CampusGroupDTO campusGroupDTO);

    List<CampusGroupDTO> toDtoList(List<CampusGroup> campusGroups);
}
