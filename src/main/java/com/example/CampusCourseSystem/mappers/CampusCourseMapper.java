package com.example.CampusCourseSystem.mappers;

import com.example.CampusCourseSystem.dto.CampusCourse.CampusCourseDTO;
import com.example.CampusCourseSystem.dto.CampusCourse.CampusCourseDetailsDTO;
import com.example.CampusCourseSystem.models.CampusCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = CampusEntityMapper.class)
public interface CampusCourseMapper {
    @Mapping(target = "status", source = "courseStatus")
    @Mapping(target = "remainingSlotsCount", source = "campusCourse", qualifiedByName = "calculateRemainingSlots")
    CampusCourseDTO toDto(CampusCourse campusCourse);

    @Named("calculateRemainingSlots")
    default Integer calculateRemainingSlots(CampusCourse campusCourse) {
        return campusCourse.getMaximumStudentsCount() - campusCourse.getStudents().size();
    }

    @Mapping(target = "status", source = "courseStatus")
    @Mapping(target = "teachers", source = "teachers")
    @Mapping(target = "students", source = "students")
    @Mapping(target = "notifications", source = "notifications")
    CampusCourseDetailsDTO toCourseDetailsDTO(CampusCourse campusCourse);


}
