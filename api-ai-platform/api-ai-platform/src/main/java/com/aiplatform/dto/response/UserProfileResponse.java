package com.aiplatform.dto.response;

import com.aiplatform.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserProfileResponse {

    private Long id;

    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String mobile;
    private String gender;
    private Integer userType;

    private Boolean isLoggedIn;
    private Boolean isActive ;
    private Boolean isEmailVerified;
    private LocalDateTime lastLogin;

    private String fiscalCode;
    private String vatNumber;
    private String destinationCode;
    private String emailPEC;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserProfileResponse fromEntity(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .gender(user.getGender())
                .userType(user.getUserType())
                .isLoggedIn(user.getIsLoggedIn())
                .isActive(user.getIsActive())
                .isEmailVerified(user.getIsEmailVerified())
                .lastLogin(user.getLastLogin())
                .fiscalCode(user.getFiscalCode())
                .vatNumber(user.getVatNumber())
                .destinationCode(user.getDestinationCode())
                .emailPEC(user.getEmailPEC())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }



}
