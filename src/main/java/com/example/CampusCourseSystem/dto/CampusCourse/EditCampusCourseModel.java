package com.example.CampusCourseSystem.dto.CampusCourse;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCampusCourseModel {

    @NotBlank(message = "Требования не должны быть пустыми")
    private String requirements;

    @NotBlank(message = "Аннотации не должны быть пустыми")
    private String annotations;
}
