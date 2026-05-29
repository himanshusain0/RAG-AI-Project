package com.aiplatform.service;

import com.aiplatform.dto.request.LoginRequest;
import com.aiplatform.dto.request.SignupRequest;
import com.aiplatform.dto.response.LoginResponse;
import com.aiplatform.dto.response.UserProfileResponse;
import com.aiplatform.model.User;
import com.aiplatform.repository.ProjectRepository;
import com.aiplatform.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    // UserRepository directly nahi — UserService ke through
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final ProjectRepository  projectRepository;

    public UserProfileResponse signup(SignupRequest request) {

        // UserService.existsByEmailOrMobile()
        if (userService.existsByEmailOrMobile(
                request.getEmail(), request.getMobile())) {
            throw new RuntimeException("Email or mobile already registered");
        }

        // User object banao
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fullName(request.getFirstName() + " " + request.getLastName())
                .email(request.getEmail().toLowerCase())
                .mobile(request.getMobile())
                .gender(request.getGender())
                .password(request.getPassword())   // UserService.createUser() mein hash hoga
                .Address(request.getAddress())
                .city(request.getCity())
                .zipcode(request.getZipcode())
                .province(request.getProvince())
                .country(request.getCountry())
                .countryCode(request.getCountyCode())
                .userType(3)
                .isActive(true)
                .isLoggedIn(false)
                .isEmailVerified(false)
                .build();

        // UserService.createUser() → password hash + save
        User saved = userService.createUser(user);
        return UserProfileResponse.fromEntity(saved);
    }

    public LoginResponse login(LoginRequest request) {

        // UserService.findByEmailWithPassword()
        User user = userService
                .findByEmailWithPassword(
                        request.getEmail().toLowerCase(),
                        request.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new RuntimeException("Account is deactivated");
        }

        // Abhi dummy token — JWT baad mein
        List<Long> projectIds = projectRepository
                .findByUserAndIsActive(user, true)
                .stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());

        // UserService.updateLoginStatus()
        String token = jwtUtil.generateToken(user, projectIds);
        userService.updateLoginStatus(user.getId(), true, token);

        return LoginResponse.builder()
                .token(token)
                .user(UserProfileResponse.fromEntity(user))
                .build();
    }

    public UserProfileResponse getProfile(Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserProfileResponse.fromEntity(user);
    }
}