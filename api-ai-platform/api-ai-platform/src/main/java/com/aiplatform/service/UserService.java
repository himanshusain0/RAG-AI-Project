package com.aiplatform.service;


import com.aiplatform.model.User;
import com.aiplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
