package com.example.CampusCourseSystem.mappers;

import com.example.CampusCourseSystem.dto.CampusCourseDTO;
import com.example.CampusCourseSystem.models.CampusCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CampusCourseMapper {
    @Mapping(target = "remainingSlotsCount", source = "campusCourse", qualifiedByName = "calculateRemainingSlots")
    CampusCourseDTO toDto(CampusCourse campusCourse);

    @Named("calculateRemainingSlots")
    default Integer calculateRemainingSlots(CampusCourse campusCourse) {
        return campusCourse.getMaximumStudentsCount() - campusCourse.getStudents().size();
    }
}
