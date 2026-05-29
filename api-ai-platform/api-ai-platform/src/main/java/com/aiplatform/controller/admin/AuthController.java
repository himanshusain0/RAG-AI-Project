package com.aiplatform.controller.admin;

import com.aiplatform.dto.request.LoginRequest;
import com.aiplatform.dto.request.SignupRequest;
import com.aiplatform.dto.response.ApiResponse;
import com.aiplatform.dto.response.LoginResponse;
import com.aiplatform.dto.response.UserProfileResponse;
import com.aiplatform.model.User;
import com.aiplatform.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class AuthController {


    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserProfileResponse>> signIn(@Valid @RequestBody SignupRequest signupRequest){
        UserProfileResponse data = authService.signup(signupRequest);
        return  ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Signup successful" , data ));

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        LoginResponse data = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", data));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof String) {
            throw new RuntimeException("User not authenticated properly");
        }

        User user = (User) principal;
        UserProfileResponse data = authService.getProfile(user.getId());
        return ResponseEntity.ok(ApiResponse.success("Success", data));
    }
}
