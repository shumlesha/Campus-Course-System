package com.example.CampusCourseSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class UserRegisterModel {

    @NotBlank(message = "Необходимо полное имя")
    @Length(min = 1, message = "Имя должно содержать хотя-бы 1 символ")
    private String fullName;

    @Past(message = "Дата рождения дожна быть прошедшей")
    private LocalDateTime birthDate;

    @NotBlank(message = "Необходим email")
    @Email(message = "Необходим валидный email")
    private String email;

    @NotBlank(message = "Необходим пароль")
    @Length(min = 6, max = 32, message = "Длина пароля должна быть от 6 до 32 символов")
    private String password;

    @NotBlank(message = "Необходимо подтверждение пароля")
    @Length(min = 6, max = 32, message = "Длина пароля должна быть от 6 до 32 символов")
    private String confirmPassword;


}
