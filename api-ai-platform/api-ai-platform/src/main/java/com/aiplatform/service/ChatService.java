package com.aiplatform.service;

import com.aiplatform.model.*;
import com.aiplatform.repository.*;
import lombok.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ProjectRepository projectRepository;
    private final MessageRepository messageRepository;
    private final ChatSessionService chatSessionService;

    public ChatAnswer askQuestion(String apiKey, String question, String sessionId) {


        Project project = projectRepository.findByApiKeyAndIsActive(apiKey, true)
                .orElseThrow(() -> new RuntimeException("Invalid API key"));


        ChatSession session = chatSessionService
                .findOrCreateSession(sessionId, project);


        messageRepository.save(Message.builder()
                .session(session)
                .project(project)
                .role("user")
                .content(question)
                .build());


        String answer = "Answer will be generated after embedding setup.";


        messageRepository.save(Message.builder()
                .session(session)
                .project(project)
                .role("bot")
                .content(answer)
                .build());

        return new ChatAnswer(answer);
    }

    @Data
    @AllArgsConstructor
    public static class ChatAnswer {
        private String answer;
    }
}