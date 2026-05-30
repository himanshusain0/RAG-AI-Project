package com.aiplatform.repository;

import com.aiplatform.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {


    List<Message> findBySessionIdOrderByCreatedAtAsc(Long sessionId);


    List<Message> findByProjectId(Long projectId);


}
