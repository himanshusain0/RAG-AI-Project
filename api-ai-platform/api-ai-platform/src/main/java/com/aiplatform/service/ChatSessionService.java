package com.aiplatform.service;

import com.aiplatform.dto.request.CreateSessionRequest;
import com.aiplatform.dto.response.ChatSessionResponse;
import com.aiplatform.model.ChatSession;
import com.aiplatform.model.Project;
import com.aiplatform.repository.ChatSessionRepository;
import com.aiplatform.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatSessionService {

    private final ChatSessionRepository chatSessionRepository;
    private final ProjectRepository projectRepository;

    // ChatSession.create({ ...req.body, sessionId })
    public ChatSessionResponse createSession(CreateSessionRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // crypto.randomBytes(12).toString('hex') ka replacement
        String sessionId = UUID.randomUUID().toString().replace("-", "")
                .substring(0, 24);

        ChatSession session = ChatSession.builder()
                .project(project)
                .sessionId(sessionId)
                .name(request.getName())
                .email(request.getEmail())
                .isActive(true)
                .build();

        return ChatSessionResponse.fromEntity(chatSessionRepository.save(session));
    }

    // ChatSession.find({ projectId })
    public List<ChatSessionResponse> getSessionsByProject(Long projectId) {
        return chatSessionRepository.findByProjectId(projectId)
                .stream()
                .map(ChatSessionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // ChatSession.findById()
    public ChatSessionResponse getSessionById(Long id) {
        ChatSession session = chatSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        return ChatSessionResponse.fromEntity(session);
    }

    // ChatSession.findByIdAndDelete()
    public void deleteSession(Long id) {
        chatSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        chatSessionRepository.deleteById(id);
    }


    public ChatSession findOrCreateSession(String sessionId, Project project) {
        return chatSessionRepository.findBySessionId(sessionId)
                .orElseGet(() -> chatSessionRepository.save(
                        ChatSession.builder()
                                .project(project)
                                .sessionId(sessionId)
                                .isActive(true)
                                .build()
                ));
    }
}