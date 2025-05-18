package br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.dto;

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
// Class LinkUser for the link_user create and update operations
public class ResourcesUserDTO {

    // ID of LinkUser
    private Long id;

    private Long idResource;

    // User
    private Long idUser;
}


