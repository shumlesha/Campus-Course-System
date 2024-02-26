package com.example.CampusCourseSystem.dto.CampusCourse;


import com.example.CampusCourseSystem.enums.CourseStatuses;
import com.example.CampusCourseSystem.enums.StudentStatuses;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCourseStudentStatusModel {

    @NotBlank(message = "Укажите статус студента")
    private StudentStatuses status;
}
