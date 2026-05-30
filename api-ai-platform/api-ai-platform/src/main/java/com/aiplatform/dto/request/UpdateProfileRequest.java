package com.aiplatform.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileRequest {

    private String firstName;
    private String lastName;
    private String mobile;
    private String gender;

    // Company
    private String companyName;
    private String fiscalCode;
    private String vatNumber;
    private String destinationCodice;
    private String emailPEC;

    // Billing Address
    private String billingAddress;
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

    // Basic Address
    private String address;
    private String city;
    private String zipcode;
    private String province;
    private String country;
    private String countryCode;
}