package com.example.CampusCourseSystem.dto.Account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class EditUserProfileModel {

    @NotBlank(message = "Полное имя не может быть пустым")
    @Length(min = 1, message = "Имя должно быть хотя бы из 1 символа")
    private String fullName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime birthDate;

}
