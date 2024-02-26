package com.example.CampusCourseSystem.repository;

import com.example.CampusCourseSystem.models.CampusCourseTeacher;
import com.example.CampusCourseSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampusCourseTeacherRepository extends JpaRepository<CampusCourseTeacher, UUID> {

    List<CampusCourseTeacher> findByTeacher(User teacher);
}
