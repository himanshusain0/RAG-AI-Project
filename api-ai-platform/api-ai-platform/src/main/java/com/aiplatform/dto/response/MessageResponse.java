package com.aiplatform.dto.response;


import com.aiplatform.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private Long id;
    private Long sessionId;
    private Long projectId;
    private String role ;
    private String content ;
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt ;


    public static MessageResponse fromEntity(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .sessionId(message.getSession().getId())
                .projectId(message.getProject().getId())
                .role(message.getRole())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .build();
    }
}
