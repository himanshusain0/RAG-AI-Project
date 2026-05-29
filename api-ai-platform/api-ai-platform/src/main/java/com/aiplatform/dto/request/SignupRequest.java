package com.aiplatform.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Please enter a valid email"
    )
//    @Email(message = "Invalid Emial format")
    private String email;

    @NotBlank(message = "mobile is Required ")
    private String mobile;


    @NotBlank(message = "password is required")
    @Size(min = 6 ,message = "Password is min 6 characters ")
    private String password;

    private String gender;
    private String address;
    private String city;
    private String zipcode;
    private String country;
    private String province;
    private String countyCode;



}
