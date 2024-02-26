package com.example.CampusCourseSystem.services;

import com.example.CampusCourseSystem.dto.CampusCourse.*;
import com.example.CampusCourseSystem.enums.CourseStatuses;
import com.example.CampusCourseSystem.security.JwtEntity;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    CampusCourseDetailsDTO getCourseDetails(String email, UUID id);

    void signUpToCourse(UUID userId, UUID id);

    void editCourseStatus(UUID id, JwtEntity jwtEntity, CourseStatuses status);

    void editStudentStatus(UUID id, UUID studentId, EditCourseStudentStatusModel editCourseStudentStatusModel, JwtEntity jwtEntity);

    void createNotification(UUID id, AddCampusCourseNotificationModel addCampusCourseNotificationModel, JwtEntity jwtEntity);

    void editStudentMark(UUID courseId, UUID studentId, EditCourseStudentMarkModel editCourseStudentMarkModel, JwtEntity jwtEntity);

    void createCampusCourse(UUID groupId, CreateCampusCourseModel createCampusCourseModel, JwtEntity jwtEntity);

    void editCampusCourse(UUID id, EditCampusCourseModel editCampusCourseModel, JwtEntity jwtEntity);

    void deleteCampusCourse(UUID id, JwtEntity jwtEntity);

    void addTeacherToCourse(UUID courseId, UUID userId, JwtEntity jwtEntity);

    List<CampusCourseDTO> getMyCourses(JwtEntity jwtEntity);

    List<CampusCourseDTO> getTeachingCourses(UUID teacherId);
}
