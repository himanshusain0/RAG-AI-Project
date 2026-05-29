package com.aiplatform.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id" , nullable = false)
    private User user;

    @Column(nullable = false , unique = true)
    private String apiKey;

    @ElementCollection
    @CollectionTable(
            name ="project_allowed_domain",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name ="domain")
    private List<String> allowedDomains;


    @Builder.Default
    private Boolean isActive = true;


    @CreationTimestamp
    @Column(updatable = false)
    private  LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
