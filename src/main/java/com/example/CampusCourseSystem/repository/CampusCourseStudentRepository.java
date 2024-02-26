package com.example.CampusCourseSystem.repository;

import com.example.CampusCourseSystem.models.CampusCourse;
import com.example.CampusCourseSystem.models.CampusCourseStudent;
import com.example.CampusCourseSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CampusCourseStudentRepository extends JpaRepository<CampusCourseStudent, UUID> {

    Optional<CampusCourseStudent> findByCampusCourseAndStudent(CampusCourse course, User student);

    Optional<CampusCourseStudent> findByCampusCourseIdAndStudentId(UUID courseId, UUID studentId);

    List<CampusCourseStudent> findByStudent(User user);
}
