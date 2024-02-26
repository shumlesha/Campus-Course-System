package com.example.CampusCourseSystem.controller;

import com.example.CampusCourseSystem.dto.CampusCourse.CampusCourseDTO;
import com.example.CampusCourseSystem.dto.CampusGroup.CampusGroupDTO;
import com.example.CampusCourseSystem.dto.CampusGroup.CreateCampusGroupModel;
import com.example.CampusCourseSystem.dto.CampusGroup.EditCampusGroupModel;
import com.example.CampusCourseSystem.services.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Group")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;

    @Operation(summary = "Get list of all campus groups")
    @GetMapping()
    public ResponseEntity<List<CampusGroupDTO>> getAllGroups() {
        List<CampusGroupDTO> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @Operation(summary = "Create campus group")
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createGroup(@Validated @RequestBody CreateCampusGroupModel createCampusGroupModel) {
        groupService.createGroup(createCampusGroupModel);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Edit campus group name")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editGroup(@PathVariable UUID id, @Validated @RequestBody EditCampusGroupModel editCampusGroupModel) {
        groupService.editGroup(id, editCampusGroupModel);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete campus group")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get list of campus courses of the campus group")
    @GetMapping("/{id}")
    public ResponseEntity<List<CampusCourseDTO>> getCoursesByGroup(@PathVariable UUID id) {
        return ResponseEntity.ok(groupService.getCoursesByGroup(id));
    }
}
