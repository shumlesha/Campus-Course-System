package com.example.CampusCourseSystem.dto.Account;

import com.example.CampusCourseSystem.enums.StudentMarks;
import com.example.CampusCourseSystem.enums.StudentStatuses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private UUID id;
    private String name;
    private StudentStatuses status;
    private StudentMarks midtermResult;
    private StudentMarks finalResult;
}
