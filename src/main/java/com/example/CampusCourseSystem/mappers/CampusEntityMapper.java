package com.example.CampusCourseSystem.mappers;

import com.example.CampusCourseSystem.dto.NotificationDTO;
import com.example.CampusCourseSystem.dto.Account.StudentDTO;
import com.example.CampusCourseSystem.dto.Account.TeacherDTO;
import com.example.CampusCourseSystem.models.CampusCourseStudent;
import com.example.CampusCourseSystem.models.CampusCourseTeacher;
import com.example.CampusCourseSystem.models.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CampusEntityMapper {
    @Mapping(source = "teacher.fullName", target = "name")
    @Mapping(source = "teacher.email", target = "email")
    TeacherDTO toTeacherDTO(CampusCourseTeacher teacher);


    @Mapping(source = "student.fullName", target = "name")
    @Mapping(source = "studentStatus", target = "status")
    @Mapping(source = "midtermResult", target = "midtermResult")
    @Mapping(source = "finalResult", target = "finalResult")
    StudentDTO toStudentDTO(CampusCourseStudent student);
    NotificationDTO toNotificationDTO(Notification notification);
}
