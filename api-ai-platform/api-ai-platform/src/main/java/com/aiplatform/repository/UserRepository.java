package com.aiplatform.repository;

import com.aiplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);
    boolean existsByEmailOrMobile(String email, String mobile);
    @org.springframework.data.jpa.repository.Query(
            "SELECT u FROM User u WHERE u.email = email"
    )
    Optional<User> findByEmailWithPassword(String email, String Password);

}
