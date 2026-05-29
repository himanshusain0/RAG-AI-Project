package com.aiplatform.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProjectRequest {


    @NotBlank(message = "Project Name is required")
    private String name;

    private String description;


    private List<String> allowedDomains;

}
