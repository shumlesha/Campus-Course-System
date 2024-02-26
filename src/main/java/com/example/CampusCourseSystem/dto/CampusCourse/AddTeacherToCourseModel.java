package com.example.CampusCourseSystem.dto.CampusCourse;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTeacherToCourseModel {
    @NotNull(message = "id не может быть null")
    private UUID userId;
}
