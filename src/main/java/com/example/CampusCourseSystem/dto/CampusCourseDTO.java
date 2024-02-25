package com.example.CampusCourseSystem.dto;

import com.example.CampusCourseSystem.enums.CourseStatuses;
import com.example.CampusCourseSystem.enums.Semesters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampusCourseDTO {
    private UUID id;
    private String name;
    private Integer startYear;
    private Integer maximumStudentsCount;
    private Integer remainingSlotsCount;
    private CourseStatuses status;
    private Semesters semester;
}
