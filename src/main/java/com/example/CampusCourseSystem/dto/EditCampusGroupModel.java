package com.example.CampusCourseSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCampusGroupModel {
    @NotBlank(message = "Имя группы не должно быть пустым")
    @Length(min = 1, message = "Имя должно состоять хотя-бы из 1 символа")
    private String name;
}
