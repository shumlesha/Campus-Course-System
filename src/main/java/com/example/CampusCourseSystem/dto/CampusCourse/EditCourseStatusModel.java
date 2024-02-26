package com.example.CampusCourseSystem.dto.CampusCourse;

import com.example.CampusCourseSystem.enums.CourseStatuses;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCourseStatusModel {

    @NotBlank(message = "Необходимо указать статус")
    private CourseStatuses status;
}
