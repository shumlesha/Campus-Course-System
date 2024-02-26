package com.example.CampusCourseSystem.controller;
import com.example.CampusCourseSystem.dto.CampusCourse.*;
import com.example.CampusCourseSystem.security.JwtEntity;
import com.example.CampusCourseSystem.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Course")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;


    @Operation(summary = "Get campus course's detailed info")
    @GetMapping("/{id}/details")
    public ResponseEntity<CampusCourseDetailsDTO> getCourseDetails(@AuthenticationPrincipal JwtEntity jwtEntity, @PathVariable UUID id) {
        return ResponseEntity.ok(courseService.getCourseDetails(jwtEntity.getEmail(), id));
    }

    @Operation(summary = "Sign up for a campus course")
    @PostMapping("/{id}/sign-up")
    public ResponseEntity<?> signUpToCourse(@AuthenticationPrincipal JwtEntity jwtEntity, @PathVariable UUID id) {
        courseService.signUpToCourse(jwtEntity.getId(), id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Edit campus course's status")
    @PostMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<?> editCourseStatus(@PathVariable UUID id,
                                              @RequestBody EditCourseStatusModel editCourseStatusModel,
                                              @AuthenticationPrincipal JwtEntity jwtEntity) {
        courseService.editCourseStatus(id, jwtEntity, editCourseStatusModel.getStatus());
        return ResponseEntity.ok("Статус курса обновлен");
    }


    @Operation(summary = "Edit status of the student that signed up for the course")
    @PostMapping("/{id}/student-status/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<?> editStudentStatus(@PathVariable UUID id,
                                               @PathVariable UUID studentId,
                                               @RequestBody EditCourseStudentStatusModel editCourseStudentStatusModel,
                                               @AuthenticationPrincipal JwtEntity jwtEntity) {
        courseService.editStudentStatus(id, studentId, editCourseStudentStatusModel, jwtEntity);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Create new course notification")
    @PostMapping("/{id}/notifications")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<?> createNotification(@PathVariable UUID id,
                                                @Validated @RequestBody AddCampusCourseNotificationModel addCampusCourseNotificationModel,
                                                @AuthenticationPrincipal JwtEntity jwtEntity) {
        courseService.createNotification(id, addCampusCourseNotificationModel, jwtEntity);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Edit mark of the student studying the campus course")
    @PostMapping("/{courseId}/marks/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<?> editStudentMark(@PathVariable UUID courseId,
            @PathVariable UUID studentId,
            @Validated @RequestBody EditCourseStudentMarkModel editCourseStudentMarkModel,
            @AuthenticationPrincipal JwtEntity jwtEntity) {
        courseService.editStudentMark(courseId, studentId, editCourseStudentMarkModel, jwtEntity);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Create new campus course for the campus group")
    @PostMapping("/{groupId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCampusCourse(@PathVariable UUID groupId,
            @Validated @RequestBody CreateCampusCourseModel createCampusCourseModel,
            @AuthenticationPrincipal JwtEntity jwtEntity) {
        courseService.createCampusCourse(groupId, createCampusCourseModel, jwtEntity);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Edit campus course's annotations and requirements")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<?> editCampusCourse(@PathVariable UUID id,
                                              @Validated @RequestBody EditCampusCourseModel editCampusCourseModel,
                                              @AuthenticationPrincipal JwtEntity jwtEntity) {
        courseService.editCampusCourse(id, editCampusCourseModel, jwtEntity);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete campus course")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCampusCourse(@PathVariable UUID id,
                                                @AuthenticationPrincipal JwtEntity jwtEntity) {
        courseService.deleteCampusCourse(id, jwtEntity);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Add campus course's teacher role to user")
    @PostMapping("/{id}/teachers")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<?> addTeacherToCourse(@PathVariable UUID id,
                                                @RequestBody AddTeacherToCourseModel addTeacherToCourseModel,
                                                @AuthenticationPrincipal JwtEntity jwtEntity) {
        courseService.addTeacherToCourse(id, addTeacherToCourseModel.getUserId(), jwtEntity);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get list of campus courses user has signed up for")
    @GetMapping("/my")
    public ResponseEntity<List<CampusCourseDTO>> getMyCourses(@AuthenticationPrincipal JwtEntity jwtEntity) {
        List<CampusCourseDTO> myCourses = courseService.getMyCourses(jwtEntity);
        return ResponseEntity.ok(myCourses);
    }


    @Operation(summary = "Get list of campus courses user is teaching")
    @GetMapping("/teaching")
    public ResponseEntity<List<CampusCourseDTO>> getTeachingCourses(@AuthenticationPrincipal JwtEntity jwtEntity) {
        List<CampusCourseDTO> courses = courseService.getTeachingCourses(jwtEntity.getId());
        return ResponseEntity.ok(courses);
    }

}
