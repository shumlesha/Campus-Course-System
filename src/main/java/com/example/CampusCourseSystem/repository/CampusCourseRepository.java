package com.example.CampusCourseSystem.repository;


import com.example.CampusCourseSystem.models.CampusCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampusCourseRepository extends JpaRepository<CampusCourse, UUID> {
    List<CampusCourse> findAllByCampusGroupId(UUID campusGroupId);

}
