package com.aiplatform.dto.response;

import com.aiplatform.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailResponse {

    // UserTransformer.userDetail() ka exact conversion
    // userProfile se zyada fields hain isme
    private Long id;

    // Basic Info
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String mobile;
    private String gender;
    private Integer userType;

    // Status
    private Boolean isLoggedIn;
    private Boolean isActive;
    private Boolean isEmailVerified;
    private LocalDateTime lastLogin;

    // Profile / Invoice (companyName bhi hai — userProfile mein nahi tha)
    private String companyName;
    private String fiscalCode;
    private String vatNumber;
    private String destinationCodice;
    private String emailPEC;

    // Billing Address
    private String billingAddressCity;
    private String billingAddressProvince;
    private String billingAddressZipcode;
    private String billingAddressCountryCode;
    private String billingAddressCountry;

    // Residence Address
    private String residenceAddress;
    private String residenceAddressCity;
    private String residenceAddressProvince;
    private String residenceAddressZipcode;
    private String residenceAddressCountryCode;
    private String residenceAddressCountry;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDetailResponse fromEntity(User user) {
        return UserDetailResponse.builder()
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
                .companyName(user.getCompanyName())
                .fiscalCode(user.getFiscalCode())
                .vatNumber(user.getVatNumber())
                .destinationCodice(user.getDestinationCode())
                .emailPEC(user.getEmailPEC())
                .billingAddressCity(user.getBillingAddressCity())
                .billingAddressProvince(user.getBillingAddressProvince())
                .billingAddressZipcode(user.getBillingAddressZipCode())
                .billingAddressCountryCode(user.getBillingAddressCountyCode())
                .billingAddressCountry(user.getBillingAddressCountry())
                .residenceAddress(user.getResidenceAddress())
                .residenceAddressCity(user.getResidenceAddressCity())
                .residenceAddressProvince(user.getResidenceAddressProvince())
                .residenceAddressZipcode(user.getResidenceAddressZipCode())
                .residenceAddressCountryCode(user.getBillingAddressCountyCode())
                .residenceAddressCountry(user.getResidenceAddressCountry())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}