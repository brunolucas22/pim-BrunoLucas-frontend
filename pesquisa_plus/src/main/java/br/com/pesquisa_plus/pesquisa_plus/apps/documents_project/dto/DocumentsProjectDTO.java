package br.com.pesquisa_plus.pesquisa_plus.apps.documents_project.dto;

// Imports
import java.math.BigDecimal;
import java.sql.Timestamp;

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
// Class DocumentsProject for the documents_project create and update operations
public class DocumentsProjectDTO {

    // ID of DocumentsProject
    private Long id;

    // Name of DocumentsProject
    @NotBlank(message = "Título do documento é obrigatório!")
    @Size(max = 255, message = "Título do documento deve ter no máximo 255 caracteres!")
    private String titleDocumentProject;

    // Value of DocumentsProject
    private String documentDocumentProject;

    // Description of DocumentsProject
    private String descriptionDocumentProject;

    private Timestamp dateTime;

    // Project
    private Long idProject;
}


