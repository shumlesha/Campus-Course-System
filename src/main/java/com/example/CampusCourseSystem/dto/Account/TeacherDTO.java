package com.example.CampusCourseSystem.dto.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private String name;
    private String email;
    private boolean isMain;
}
