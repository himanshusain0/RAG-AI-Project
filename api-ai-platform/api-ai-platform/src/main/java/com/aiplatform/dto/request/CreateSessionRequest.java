package com.aiplatform.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSessionRequest {

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private String name;
    private String email;
    // sessionId → Service mein generate hoga (crypto.randomBytes)
}