package com.aiplatform.controller.chat;

import com.aiplatform.dto.response.ApiResponse;
import com.aiplatform.service.ChatService;
import lombok.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // POST /chat/ask — public route, apiKey se auth hota hai
    @PostMapping("/ask")
    public ResponseEntity<ApiResponse<ChatService.ChatAnswer>> askQuestion(
            @RequestBody AskRequest request) {
        ChatService.ChatAnswer answer = chatService.askQuestion(
                request.getApiKey(),
                request.getQuestion(),
                request.getSessionId());
        return ResponseEntity.ok(ApiResponse.success("Answer generated", answer));
    }

    @Data
    public static class AskRequest {
        private String apiKey;
        private String question;
        private String sessionId;
    }
}