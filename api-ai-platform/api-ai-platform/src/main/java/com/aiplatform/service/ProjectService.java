package com.aiplatform.service;


import com.aiplatform.dto.request.CreateProjectRequest;
import com.aiplatform.dto.response.ProjectResponse;
import com.aiplatform.model.Project;
import com.aiplatform.model.User;
import com.aiplatform.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {


    private final ProjectRepository projectRepository;

    public ProjectResponse createProject(CreateProjectRequest request , User user){
        Project project  = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .user(user)
                .apiKey(generateApiKey())
                .allowedDomains(request.getAllowedDomains())
                .isActive(true)
                .build();

        return ProjectResponse.fromEntity(projectRepository.save(project));
    }

    public List<ProjectResponse> getUserProjects(User user){
        return projectRepository.findByUserAndIsActive(user , true)
                .stream()
                .map(ProjectResponse::fromEntity)
                .collect((Collectors.toList()));
    }

    public ProjectResponse getProjectById(Long id  , User user){
        Project project = projectRepository.findByIdAndUser(id, user).orElseThrow(()-> new RuntimeException("Project not found"));
        return  ProjectResponse.fromEntity(project);
    }

    public ProjectResponse updateProject(Long id , CreateProjectRequest request , User user){
        Project project = projectRepository.findByIdAndUser(id,user).orElseThrow(()-> new RuntimeException("Project not found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setAllowedDomains(request.getAllowedDomains());

        return  ProjectResponse.fromEntity(projectRepository.save(project));
    }

    public void deleteProject(Long id , User user){

        Project project = projectRepository.findByIdAndUser(id,user)
                .orElseThrow(()-> new RuntimeException("Project not found"));

        project.setIsActive(false);
        projectRepository.save(project);

    }

    public Project getProjectByApiKey(String apiKey){
        return  projectRepository.findByApiKeyAndIsActive(apiKey , true)
                .orElseThrow(()-> new RuntimeException("Project not found"));

    }
    public String generateApiKey(){
        return  UUID.randomUUID().toString().replace("-" , "");

    }
}
