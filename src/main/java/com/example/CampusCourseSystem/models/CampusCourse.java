package com.example.CampusCourseSystem.models;

import com.example.CampusCourseSystem.enums.CourseStatuses;
import com.example.CampusCourseSystem.enums.Semesters;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;
@Data
@Entity
@Table(name = "campus_courses")
public class CampusCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "start_year", nullable = false)
    private Integer startYear;

    @Column(name = "maximum_students_count", nullable = false)
    private Integer maximumStudentsCount;

    @Column(nullable = false)
    private String requirements;

    @Column(nullable = false)
    private String annotation;

    @Enumerated(EnumType.STRING)
    private Semesters semester;

    @Enumerated(EnumType.STRING)
    private CourseStatuses courseStatus;


    @OneToMany(mappedBy = "campusCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CampusCourseTeacher> teachers;

    @OneToMany(mappedBy = "campusCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CampusCourseStudent> students;

    @OneToMany(mappedBy = "campusCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications;

    @ManyToOne
    @JoinColumn(name = "campus_group_id")
    private CampusGroup campusGroup;
}
