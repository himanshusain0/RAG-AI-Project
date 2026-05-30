package com.aiplatform.controller;

import com.aiplatform.dto.request.UpdateProfileRequest;
import com.aiplatform.dto.response.ApiResponse;
import com.aiplatform.dto.response.UserDetailResponse;
import com.aiplatform.model.User;
import com.aiplatform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (principal instanceof String) {
            throw new RuntimeException("User not authenticated");
        }
        return (User) principal;
    }

    // GET /admin/api/user/profile — UserTransformer.userDetail()
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getProfile() {
        User user = getCurrentUser();
        return ResponseEntity.ok(
                ApiResponse.success("Success",
                        UserDetailResponse.fromEntity(user)));
    }

    // PUT /admin/api/user/profile — profile update
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserDetailResponse>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {
        User user = getCurrentUser();
        UserDetailResponse data = userService.updateProfile(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", data));
    }
}