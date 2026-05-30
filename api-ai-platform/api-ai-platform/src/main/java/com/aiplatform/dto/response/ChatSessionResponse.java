package com.aiplatform.dto.response;

import com.aiplatform.model.ChatSession;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatSessionResponse {

    private Long id;
    private String sessionId;
    private Long projectId;
    private String name;
    private String email;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ChatSessionResponse fromEntity(ChatSession session) {
        return ChatSessionResponse.builder()
                .id(session.getId())
                .sessionId(session.getSessionId())
                .projectId(session.getProject().getId())
                .name(session.getName())
                .email(session.getEmail())
                .isActive(session.getIsActive())
                .createdAt(session.getCreatedAt())
                .updatedAt(session.getUpdatedAt())
                .build();
    }
}