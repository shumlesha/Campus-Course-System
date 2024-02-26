package com.example.CampusCourseSystem.services.impl;

import com.example.CampusCourseSystem.dto.CampusCourse.*;
import com.example.CampusCourseSystem.enums.*;
import com.example.CampusCourseSystem.exceptions.ResourceNotFoundException;
import com.example.CampusCourseSystem.mappers.CampusCourseMapper;
import com.example.CampusCourseSystem.mappers.CampusEntityMapper;
import com.example.CampusCourseSystem.models.*;
import com.example.CampusCourseSystem.repository.*;
import com.example.CampusCourseSystem.security.JwtEntity;
import com.example.CampusCourseSystem.services.CourseService;
import com.example.CampusCourseSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CampusCourseRepository campusCourseRepository;
    private final UserService userService;
    private final CampusEntityMapper campusEntityMapper;
    private final CampusCourseMapper campusCourseMapper;
    private final CampusCourseStudentRepository campusCourseStudentRepository;
    private final NotificationRepository notificationRepository;
    private final CampusGroupRepository campusGroupRepository;
    private final CampusCourseTeacherRepository campusCourseTeacherRepository;
    @Override
    public CampusCourseDetailsDTO getCourseDetails(String email, UUID id) {
        CampusCourse course = campusCourseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Нет курса с id " + id));

        User user = userService.getByEmail(email);

        CampusCourseDetailsDTO courseDetails = campusCourseMapper.toCourseDetailsDTO(course);;


        if (user.getRoles().contains(Role.ROLE_ADMIN) || user.getRoles().contains(Role.ROLE_TEACHER)) {
            courseDetails.setTeachers(course.getTeachers().stream().map(campusEntityMapper::toTeacherDTO).collect(Collectors.toList()));
            courseDetails.setNotifications(course.getNotifications().stream().map(campusEntityMapper::toNotificationDTO).collect(Collectors.toList()));
            courseDetails.setStudents(course.getStudents().stream().map(campusEntityMapper::toStudentDTO).collect(Collectors.toList()));
        } else if (user.getRoles().contains(Role.ROLE_STUDENT)) {
            courseDetails.setStudents(course.getStudents().stream()
                    .filter(s -> s.getStudentStatus() == StudentStatuses.ACCEPTED || s.getStudent().getId().equals(user.getId()))
                    .map(campusEntityMapper::toStudentDTO)
                    .collect(Collectors.toList()));
        }

        courseDetails.setStudentsEnrolledCount((int) course.getStudents().stream()
                .filter(s -> s.getStudentStatus() == StudentStatuses.ACCEPTED).count());
        courseDetails.setStudentsInQueueCount((int) course.getStudents().stream()
                .filter(s -> s.getStudentStatus() == StudentStatuses.IN_QUEUE).count());

        return courseDetails;
    }

    @Override
    public void signUpToCourse(UUID userId, UUID id) {
        CampusCourse course = campusCourseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не существует курса с id " + id));

        User user = userService.getById(userId);

        if (!course.getCourseStatus().equals(CourseStatuses.OPEN_FOR_ASSIGNING)) {
            throw new IllegalStateException("Курс закрыт для записи");
        }

        long enrolledStudentsCount = (int) course.getStudents().stream()
                .filter(s -> s.getStudentStatus() == StudentStatuses.ACCEPTED).count();
        if (enrolledStudentsCount >= course.getMaximumStudentsCount()) {
            throw new IllegalStateException("На курсе нет свободных мест");
        }


        boolean alreadyEnrolled = course.getStudents().stream()
                .anyMatch(student -> student.getStudent().getId().equals(userId));

        if (alreadyEnrolled) {
            throw new IllegalStateException("Вы уже записаны на этот курс");
        }

        CampusCourseStudent enrollment = new CampusCourseStudent();

        enrollment.setStudent(user);
        enrollment.setStudentStatus(StudentStatuses.IN_QUEUE);
        course.addStudent(enrollment);
        campusCourseStudentRepository.save(enrollment);
    }

    @Override
    public void editCourseStatus(UUID id, JwtEntity jwtEntity, CourseStatuses status) {
        CampusCourse course = campusCourseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Курс c таким id не найден " + id));

        boolean isAccepted = jwtEntity.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") ||
                        (a.getAuthority().equals("ROLE_TEACHER") && course.getTeachers().stream()
                                .map(CampusCourseTeacher::getTeacher)
                                .anyMatch(teacher -> teacher.getId().equals(jwtEntity.getId()))));

        if (!isAccepted) {
            throw new AccessDeniedException("Вы не являетесь преподавателем на данном курсе");
        }

        course.setCourseStatus(status);
        campusCourseRepository.save(course);
    }

    @Override
    public void editStudentStatus(UUID id, UUID studentId, EditCourseStudentStatusModel editCourseStudentStatusModel, JwtEntity jwtEntity) {
        CampusCourse course = campusCourseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Нет курса с таким id " + id));

        User student = userService.getById(studentId);

        boolean isAccepted = jwtEntity.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") ||
                        (a.getAuthority().equals("ROLE_TEACHER") && course.getTeachers().stream()
                                .map(CampusCourseTeacher::getTeacher)
                                .anyMatch(teacher -> teacher.getId().equals(jwtEntity.getId()))));

        if (!isAccepted) {
            throw new AccessDeniedException("Вы не являетесь преподавателем на данном курсе");
        }

        CampusCourseStudent courseStudent = campusCourseStudentRepository
                .findByCampusCourseAndStudent(course, student)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Этого студента нет на данном курсе"));

        courseStudent.setStudentStatus(editCourseStudentStatusModel.getStatus());

        campusCourseStudentRepository.save(courseStudent);
    }

    @Override
    public void createNotification(UUID id, AddCampusCourseNotificationModel addCampusCourseNotificationModel, JwtEntity jwtEntity) {
        CampusCourse course = campusCourseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Курс c таким id не найден " + id));

        boolean isAccepted = jwtEntity.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") ||
                        (a.getAuthority().equals("ROLE_TEACHER") && course.getTeachers().stream()
                                .map(CampusCourseTeacher::getTeacher)
                                .anyMatch(teacher -> teacher.getId().equals(jwtEntity.getId()))));

        if (!isAccepted) {
            throw new AccessDeniedException("Вы не являетесь преподавателем на данном курсе");
        }

        Notification notification = new Notification();
        notification.setText(addCampusCourseNotificationModel.getText());
        notification.setImportant(addCampusCourseNotificationModel.isImportant());
        course.addNotification(notification);
        notificationRepository.save(notification);
    }

    @Override
    public void editStudentMark(UUID courseId, UUID studentId, EditCourseStudentMarkModel editCourseStudentMarkModel, JwtEntity jwtEntity) {
        CampusCourse course = campusCourseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Курс c таким id не найден " + courseId));

        CampusCourseStudent courseStudent = campusCourseStudentRepository
                .findByCampusCourseIdAndStudentId(courseId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "На этом курсе нет такого студента"));

        boolean isAccepted = jwtEntity.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") ||
                        (a.getAuthority().equals("ROLE_TEACHER") && course.getTeachers().stream()
                                .map(CampusCourseTeacher::getTeacher)
                                .anyMatch(teacher -> teacher.getId().equals(jwtEntity.getId()))));

        if (!isAccepted) {
            throw new AccessDeniedException("Вы не являетесь преподавателем на данном курсе");
        }

        if (editCourseStudentMarkModel.getMarkType() == MarkType.Midterm) {
            courseStudent.setMidtermResult(editCourseStudentMarkModel.getMark());
        } else if (editCourseStudentMarkModel.getMarkType() == MarkType.Final) {
            courseStudent.setFinalResult(editCourseStudentMarkModel.getMark());
        }

        campusCourseStudentRepository.save(courseStudent);
    }

    @Override
    public void createCampusCourse(UUID groupId, CreateCampusCourseModel createCampusCourseModel, JwtEntity jwtEntity) {
        CampusGroup group = campusGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Не найдена группа с таким id" + groupId));

        User mainTeacher = userService.getById(createCampusCourseModel.getMainTeacherId());

        if (!mainTeacher.getRoles().contains(Role.ROLE_TEACHER)) {
            Set<Role> updatedRoles = new HashSet<>(mainTeacher.getRoles());
            updatedRoles.add(Role.ROLE_TEACHER);
            mainTeacher.setRoles(updatedRoles);
            userService.save(mainTeacher);
        }

        CampusCourse course = new CampusCourse();
        course.setName(createCampusCourseModel.getName());
        course.setStartYear(createCampusCourseModel.getStartYear());
        course.setMaximumStudentsCount(createCampusCourseModel.getMaximumStudentsCount());
        course.setSemester(createCampusCourseModel.getSemester());
        course.setRequirements(createCampusCourseModel.getRequirements());
        course.setAnnotations(createCampusCourseModel.getAnnotations());
        course.setCampusGroup(group);
        course.setCourseStatus(CourseStatuses.CREATED);
        CampusCourse savedCourse = campusCourseRepository.save(course);

        CampusCourseTeacher newCourseTeacher = new CampusCourseTeacher();
        newCourseTeacher.setTeacher(mainTeacher);
        newCourseTeacher.setMain(true);
        course.addTeacher(newCourseTeacher);

        campusCourseTeacherRepository.save(newCourseTeacher);

        log.info(String.valueOf(course.getTeachers().size()));
    }

    @Override
    public void editCampusCourse(UUID id, EditCampusCourseModel editCampusCourseModel, JwtEntity jwtEntity) {
        CampusCourse course = campusCourseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Курс с указанным id не найден " + id));

        boolean isAccepted = jwtEntity.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") ||
                        (a.getAuthority().equals("ROLE_TEACHER") && course.getTeachers().stream()
                                .map(CampusCourseTeacher::getTeacher)
                                .anyMatch(teacher -> teacher.getId().equals(jwtEntity.getId()))));

        if (!isAccepted) {
            throw new AccessDeniedException("Вы не являетесь преподавателем на данном курсе");
        }


        course.setRequirements(editCampusCourseModel.getRequirements());
        course.setAnnotations(editCampusCourseModel.getAnnotations());

        campusCourseRepository.save(course);
    }

    @Override
    public void deleteCampusCourse(UUID id, JwtEntity jwtEntity) {
        if (!campusCourseRepository.existsById(id)) {
            throw new IllegalArgumentException("Курс с указанным id не найден " + id);
        }

        campusCourseRepository.deleteById(id);
    }

    @Override
    public void addTeacherToCourse(UUID courseId, UUID userId, JwtEntity jwtEntity) {
        CampusCourse course = campusCourseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Курс с указанным id не найден " + courseId));

        User teacher = userService.getById(userId);

        if (teacher == null) {
            throw new IllegalStateException("Пользователь с таким id не найден " + userId);
        }

        boolean isAccepted = jwtEntity.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") ||
                        (a.getAuthority().equals("ROLE_TEACHER") && course.getTeachers().stream().anyMatch(courseTeacher ->
                                courseTeacher.getTeacher().getId().equals(jwtEntity.getId()) && courseTeacher.isMain())));

        if (!isAccepted) {
            throw new AccessDeniedException("Вы не являетесь главным преподавателем на данном курсе");
        }


        boolean alreadyAssigned = course.getTeachers().stream()
                .anyMatch(courseTeacher -> courseTeacher.getTeacher().getId().equals(userId));
        if (alreadyAssigned) {
            throw new IllegalStateException("Преподаватель уже назначен на данный курс");
        }

        if (!teacher.getRoles().contains(Role.ROLE_TEACHER)) {
            Set<Role> updatedRoles = new HashSet<>(teacher.getRoles());
            updatedRoles.add(Role.ROLE_TEACHER);
            teacher.setRoles(updatedRoles);
            userService.save(teacher);
        }

        CampusCourseTeacher newCourseTeacher = new CampusCourseTeacher();
        newCourseTeacher.setCampusCourse(course);
        newCourseTeacher.setTeacher(teacher);
        newCourseTeacher.setMain(false);
        campusCourseTeacherRepository.save(newCourseTeacher);
    }

    @Override
    public List<CampusCourseDTO> getMyCourses(JwtEntity jwtEntity) {
        UUID userId = jwtEntity.getId();
        User user = userService.getById(userId);

        List<CampusCourseStudent> enrolledCourses = campusCourseStudentRepository.findByStudent(user);

        return enrolledCourses.stream()
                .map(CampusCourseStudent::getCampusCourse)
                .map(campusCourseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CampusCourseDTO> getTeachingCourses(UUID teacherId) {
        User teacher = userService.getById(teacherId);
        List<CampusCourseTeacher> courseTeachers = campusCourseTeacherRepository.findByTeacher(teacher);


        return courseTeachers.stream()
                .map(CampusCourseTeacher::getCampusCourse)
                .distinct()
                .map(campusCourseMapper::toDto)
                .collect(Collectors.toList());
    }

}
