package com.example.CampusCourseSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserLoginModel {

    @NotBlank(message = "Необходим email")
    @Email(message = "Неверный email")
    private String email;

    @NotBlank(message = "Необходим пароль")
    @Length(min = 1, message = "Пароль должен содержать хотя-бы 1 символ")
    private String password;
}