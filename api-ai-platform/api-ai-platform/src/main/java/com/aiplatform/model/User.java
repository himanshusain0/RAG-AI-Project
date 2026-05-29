package com.aiplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;


    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(length = 2048)
    private String loggedInToken;

    @Column(nullable = false , unique = true)
    private String mobile ;


    private String gender;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private Integer userType=3;


    @Builder.Default
    private  Boolean isLoggedIn=false;
//    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive=true;

    @Builder.Default
    private Boolean isEmailVerified=false;

    private LocalDateTime lastLogin;

    // ─── Company / Invoice ────────────────────────────

    private String companyName ;
    private String fiscalCode;
    private String vatNumber;
    private String destinationCode;
    private String emailPEC;


    // ─── Billing Address ──────────────────────────────────────

    private String billingAddress;
    private String billingAddressCity;
    private String billingAddressProvince;
    private String billingAddressZipCode;
    private String billingAddressCountyCode;
    private String billingAddressCountry;


    // ─── Residence Address ────────────────────────────────────

    private String residenceAddress;
    private String residenceAddressCity;
    private String residenceAddressProvince;;
    private String residenceAddressZipCode;
    private String residenceAddressCountyCode;
    private String residenceAddressCountry;

    // ─── Basic Address (Old) ──────────────────────────────────

    private String Address;
    private String city;
    private String zipcode;
    private String country;
    private String province;
    private String countryCode;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name ="user_tenants" ,joinColumns = @JoinColumn(name= "user_id"))
    @Column(name ="tenant_id")
    private List<String> tenants;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;



}
