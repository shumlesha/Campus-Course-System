package com.example.CampusCourseSystem.dto.CampusCourse;


import com.example.CampusCourseSystem.enums.Semesters;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCampusCourseModel {

    @NotBlank(message = "Название курса не может быть пустым")
    @Length(min = 1, message = "Название курса должно содержать хотя-бы 1 символ")
    private String name;

    @Min(value = 2000, message = "Год начала должен быть как минимум 2000")
    @Max(value = 2029, message = "Год начала не должен быть больше 2029")
    private Integer startYear;

    @NotNull(message = "Число студентов не может быть null")
    @Min(value = 1, message = "Число студентов должно быть как минимум 1")
    @Max(value = 200, message = "Число студентов не должно превышать 200")
    private Integer maximumStudentsCount;

    @NotNull(message = "Семестр не может быть null")
    private Semesters semester;

    @NotBlank(message = "Требования не могут быть пустыми")
    private String requirements;

    @NotBlank(message = "Аннотации не могут быть пустыми")
    private String annotations;

    @NotNull(message = "id преподавателя не может быть null")
    private UUID mainTeacherId;
}
