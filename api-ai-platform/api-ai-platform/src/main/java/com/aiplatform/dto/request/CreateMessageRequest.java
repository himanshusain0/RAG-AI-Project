package com.aiplatform.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMessageRequest {

    @NotNull(message =  "Session ID is required")
    private Long sessionId;


    @NotNull(message = "Project ID is required")
    private  Long projectId;


    @NotNull(message = "Role is required")
    private String role ;

    @NotNull(message = "Content is required")
    private String  content;

}
