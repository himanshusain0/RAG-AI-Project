package com.aiplatform.service;

import com.aiplatform.dto.request.CreateMessageRequest;
import com.aiplatform.dto.response.MessageResponse;
import com.aiplatform.model.ChatSession;
import com.aiplatform.model.Message;
import com.aiplatform.model.Project;
import com.aiplatform.repository.ChatSessionRepository;
import com.aiplatform.repository.MessageRepository;
import com.aiplatform.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ProjectRepository projectRepository;


    public MessageResponse createMessage(CreateMessageRequest request) {
        ChatSession session = chatSessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Message message = Message.builder()
                .session(session)
                .project(project)
                .role(request.getRole())
                .content(request.getContent())
                .build();

        return MessageResponse.fromEntity(messageRepository.save(message));
    }


    public List<MessageResponse> getMessagesBySession(Long sessionId) {
        return messageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId)
                .stream()
                .map(MessageResponse::fromEntity)
                .collect(Collectors.toList());
    }


    public void deleteMessage(Long id) {
        messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        messageRepository.deleteById(id);
    }
}