package com.aiplatform.repository;

import com.aiplatform.model.Project;
import com.aiplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUser(User user);

    List<Project> findByUserAndIsActive(User user, boolean isActive);

    Optional<Project> findByApiKey(String apiKey);

    Optional<Project> findByIdAndUser(Long id , User user);

    Optional<Project> findByApiKeyAndIsActive(String apiKey,boolean isActive);

    long countByUser(User user);

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.allowedDomains WHERE p.user = :user AND p.isActive = true" )
    List<Project> findActiveProjectsWithDomains(@Param("user") User user);




}
