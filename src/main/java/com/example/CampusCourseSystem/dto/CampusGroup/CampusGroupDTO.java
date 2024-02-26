package com.example.CampusCourseSystem.dto.CampusGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampusGroupDTO {
    private UUID id;
    private String name;
}
