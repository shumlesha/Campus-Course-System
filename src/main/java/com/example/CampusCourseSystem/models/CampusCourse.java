package com.example.CampusCourseSystem.models;

import com.example.CampusCourseSystem.enums.CourseStatuses;
import com.example.CampusCourseSystem.enums.Semesters;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
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
    private String annotations;

    @Enumerated(EnumType.STRING)
    private Semesters semester;

    @Enumerated(EnumType.STRING)
    private CourseStatuses courseStatus;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "campusCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CampusCourseTeacher> teachers = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "campusCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CampusCourseStudent> students = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "campusCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "campus_group_id")
    private CampusGroup campusGroup;


    public void addTeacher(CampusCourseTeacher teacher) {
        this.teachers.add(teacher);
        teacher.setCampusCourse(this);
    }

    public void removeTeacher(CampusCourseTeacher teacher) {
        this.teachers.remove(teacher);
        teacher.setCampusCourse(null);
    }

    public void addStudent(CampusCourseStudent student) {
        this.students.add(student);
        student.setCampusCourse(this);
    }

    public void removeStudent(CampusCourseStudent student) {
        this.students.remove(student);
        student.setCampusCourse(null);
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setCampusCourse(this);
    }

    public void removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setCampusCourse(null);
    }

}
