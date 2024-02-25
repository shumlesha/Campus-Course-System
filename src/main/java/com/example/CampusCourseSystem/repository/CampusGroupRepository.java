package com.example.CampusCourseSystem.repository;

import com.example.CampusCourseSystem.models.CampusGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CampusGroupRepository extends JpaRepository<CampusGroup, UUID> {

    boolean existsByName(String name);
}
