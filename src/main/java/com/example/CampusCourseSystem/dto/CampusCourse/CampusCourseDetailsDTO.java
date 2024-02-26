package com.example.CampusCourseSystem.dto.CampusCourse;

import com.example.CampusCourseSystem.dto.NotificationDTO;
import com.example.CampusCourseSystem.dto.Account.StudentDTO;
import com.example.CampusCourseSystem.dto.Account.TeacherDTO;
import com.example.CampusCourseSystem.enums.CourseStatuses;
import com.example.CampusCourseSystem.enums.Semesters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampusCourseDetailsDTO {
    private UUID id;
    private String name;
    private Integer startYear;
    private Integer maximumStudentsCount;
    private Integer studentsEnrolledCount;
    private Integer studentsInQueueCount;
    private String requirements;
    private String annotations;
    private CourseStatuses status;
    private Semesters semester;
    private List<StudentDTO> students;
    private List<TeacherDTO> teachers;
    private List<NotificationDTO> notifications;
}
