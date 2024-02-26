package com.example.CampusCourseSystem.models;

import com.example.CampusCourseSystem.enums.StudentMarks;
import com.example.CampusCourseSystem.enums.StudentStatuses;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "campus_course_students")
public class CampusCourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_course_id")
    private CampusCourse campusCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_status")
    private StudentStatuses studentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "midterm_result")
    private StudentMarks midtermResult;

    @Enumerated(EnumType.STRING)
    @Column(name = "final_result")
    private StudentMarks finalResult;
}
