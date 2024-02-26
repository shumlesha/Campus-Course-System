package com.example.CampusCourseSystem.dto.CampusCourse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCampusCourseNotificationModel {
    @NotBlank(message = "Необходим текст уведомления")
    @Length(min = 1, message = "В тексте уведомления должен быть хотя-бы 1 символ")
    private String text;

    @NotNull(message = "Укажите важность")
    private boolean isImportant;
}
