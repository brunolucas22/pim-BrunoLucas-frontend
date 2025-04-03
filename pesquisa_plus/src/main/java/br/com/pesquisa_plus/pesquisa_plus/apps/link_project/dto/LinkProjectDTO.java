package br.com.pesquisa_plus.pesquisa_plus.apps.link_project.dto;

// Imports
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import org.springframework.stereotype.Component;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Annotations for the dto
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
// Class LinkProject for the link_project create and update operations
public class LinkProjectDTO {

    // ID of LinkProject
    private Long id;

    // Name of LinkProject
    @NotBlank(message = "Título do link é obrigatório!")
    @Size(max = 255, message = "Título do link deve ter no máximo 255 caracteres!")
    private String titleLinkProject;

    // Value of LinkProject
    @NotBlank(message = "Link é obrigatório!")
    private String linkLinkProject;

    // Description of LinkProject
    private String descriptionLinkProject;

    // Project
    private Long idProject;
}


