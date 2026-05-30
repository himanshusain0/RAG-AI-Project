package com.aiplatform.controller.admin;

import com.aiplatform.dto.request.CreateSessionRequest;
import com.aiplatform.dto.response.ApiResponse;
import com.aiplatform.dto.response.ChatSessionResponse;
import com.aiplatform.service.ChatSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/sessions")
@RequiredArgsConstructor
public class ChatSessionController {

    private final ChatSessionService chatSessionService;

    // POST /admin/api/sessions
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<ChatSessionResponse>> createSession(
            @Valid @RequestBody CreateSessionRequest request) {
        ChatSessionResponse data = chatSessionService.createSession(request);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Session created successfully", data));
    }

    // GET /admin/api/sessions?projectId=1
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<List<ChatSessionResponse>>> getSessions(
            @RequestParam Long projectId) {
        List<ChatSessionResponse> data = chatSessionService.getSessionsByProject(projectId);
        return ResponseEntity.ok(ApiResponse.success("Sessions fetched successfully", data));
    }

    // GET /admin/api/sessions/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<ChatSessionResponse>> getSession(
            @PathVariable Long id) {
        ChatSessionResponse data = chatSessionService.getSessionById(id);
        return ResponseEntity.ok(ApiResponse.success("Session fetched successfully", data));
    }

    // DELETE /admin/api/sessions/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSession(@PathVariable Long id) {
        chatSessionService.deleteSession(id);
        return ResponseEntity.ok(ApiResponse.success("Session deleted successfully", null));
    }
}