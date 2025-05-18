package br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.dto;

// Imports

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

// Annotations for the dto
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
// Class LinkProject for the link_project create and update operations
public class ResourcesProjectDTO {

    // ID of LinkProject
    private Long id;

    private Long idResource;

    // Project
    private Long idProject;
}


