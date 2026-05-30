package com.aiplatform.service;


import com.aiplatform.model.User;
import com.aiplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.aiplatform.dto.request.UpdateProfileRequest;
import com.aiplatform.dto.response.UserDetailResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        return userRepository.save(user);
    }

    public boolean existsByEmailOrMobile(String email, String mobile){
        return userRepository.existsByEmailOrMobile(email, mobile);
    }

    public Optional<User> findByEmailWithPassword(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> bCryptPasswordEncoder.matches(password, user.getPassword()));
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public void updateLoginStatus(Long userId , boolean isLoggedIn , String token){
        userRepository.findById(userId).ifPresent(user -> {
            user.setIsLoggedIn(isLoggedIn);
            user.setLoggedInToken(token);
            userRepository.save(user);
        });
    }
    public UserDetailResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getFirstName()  != null) user.setFirstName(request.getFirstName());
        if (request.getLastName()   != null) user.setLastName(request.getLastName());
        if (request.getMobile()     != null) user.setMobile(request.getMobile());
        if (request.getGender()     != null) user.setGender(request.getGender());
        if (request.getCompanyName()!= null) user.setCompanyName(request.getCompanyName());
        if (request.getFiscalCode() != null) user.setFiscalCode(request.getFiscalCode());
        if (request.getVatNumber()  != null) user.setVatNumber(request.getVatNumber());
        if (request.getAddress()    != null) user.setAddress(request.getAddress());
        if (request.getCity()       != null) user.setCity(request.getCity());
        if (request.getCountry()    != null) user.setCountry(request.getCountry());
        if (request.getZipcode()    != null) user.setZipcode(request.getZipcode());

        // Billing Address
        if (request.getBillingAddress()     != null) user.setBillingAddress(request.getBillingAddress());
        if (request.getBillingAddressCity() != null) user.setBillingAddressCity(request.getBillingAddressCity());
        if (request.getBillingAddressCountry()!= null) user.setBillingAddressCountry(request.getBillingAddressCountry());

        // Residence Address
        if (request.getResidenceAddress()     != null) user.setResidenceAddress(request.getResidenceAddress());
        if (request.getResidenceAddressCity() != null) user.setResidenceAddressCity(request.getResidenceAddressCity());

        if (request.getFirstName() != null || request.getLastName() != null) {
            user.setFullName(user.getFirstName() + " " + user.getLastName());
        }

        return UserDetailResponse.fromEntity(userRepository.save(user));
    }
}
