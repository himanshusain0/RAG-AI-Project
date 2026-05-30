package com.aiplatform.repository;

import com.aiplatform.model.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {

    // ChatSession.find({ projectId })
    List<ChatSession> findByProjectId(Long projectId);

    // ChatSession.findOne({ sessionId })
    Optional<ChatSession> findBySessionId(String sessionId);

    // ChatSession.find({ projectId, isActive: true })
    List<ChatSession> findByProjectIdAndIsActive(Long projectId, Boolean isActive);

    long countByIsActive(Boolean isActive);
    long countByProjectIdAndIsActive(Long projectId, Boolean isActive);
}