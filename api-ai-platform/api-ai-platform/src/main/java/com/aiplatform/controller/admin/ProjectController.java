package com.aiplatform.controller.admin;

import com.aiplatform.dto.request.CreateProjectRequest;
import com.aiplatform.dto.response.ApiResponse;
import com.aiplatform.dto.response.ProjectResponse;
import com.aiplatform.model.User;
import com.aiplatform.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // X-User-Id HATA DIYA — JWT se user milta hai ab
    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Safety check — agar String aaya toh error do
        if (principal instanceof String) {
            throw new RuntimeException("User not authenticated properly");
        }

        return (User) principal;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
            @Valid @RequestBody CreateProjectRequest request) {
        ProjectResponse data = projectService.createProject(request, getCurrentUser());
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Project created successfully", data));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getProjects() {
        List<ProjectResponse> data = projectService.getUserProjects(getCurrentUser());
        return ResponseEntity.ok(ApiResponse.success("Projects fetched successfully", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProject(
            @PathVariable Long id) {
        ProjectResponse data = projectService.getProjectById(id, getCurrentUser());
        return ResponseEntity.ok(ApiResponse.success("Project fetched successfully", data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody CreateProjectRequest request) {
        ProjectResponse data = projectService.updateProject(id, request, getCurrentUser());
        return ResponseEntity.ok(ApiResponse.success("Updated successfully", data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(
            @PathVariable Long id) {
        projectService.deleteProject(id, getCurrentUser());
        return ResponseEntity.ok(ApiResponse.success("Deleted successfully", null));
    }
}