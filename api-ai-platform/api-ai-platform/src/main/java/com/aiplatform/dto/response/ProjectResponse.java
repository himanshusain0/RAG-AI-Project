package com.aiplatform.dto.response;


import com.aiplatform.model.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private String apiKey;
    private Boolean isActive;

    private List<String> allowedDomains;

    private Long usedId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static ProjectResponse fromEntity(Project project) {
        return  ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .apiKey(project.getApiKey())
                .isActive(project.getIsActive())
                .allowedDomains(project.getAllowedDomains())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
//                .userId(project.getUser().getId());


    }
}
