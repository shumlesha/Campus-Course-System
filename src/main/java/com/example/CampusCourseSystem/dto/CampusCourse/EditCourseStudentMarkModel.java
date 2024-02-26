package com.example.CampusCourseSystem.dto.CampusCourse;

import com.example.CampusCourseSystem.enums.MarkType;
import com.example.CampusCourseSystem.enums.StudentMarks;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCourseStudentMarkModel {

    @NotBlank(message = "Заполните тип оценки")
    private MarkType markType;

    @NotBlank(message = "Поле оценки обязательно")
    private StudentMarks mark;
}
