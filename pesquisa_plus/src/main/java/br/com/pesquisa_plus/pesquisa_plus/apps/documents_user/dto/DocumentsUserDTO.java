package br.com.pesquisa_plus.pesquisa_plus.apps.documents_user.dto;

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
// Class DocumentsUser for the documents_user create and update operations
public class DocumentsUserDTO {

    // ID of DocumentsUser
    private Long id;

    // Name of DocumentsUser
    @NotBlank(message = "Título do documento é obrigatório!")
    @Size(max = 255, message = "Título do documento deve ter no máximo 255 caracteres!")
    private String titleDocumentUser;

    // Value of DocumentsUser
    private String documentDocumentUser;

    // Description of DocumentsUser
    private String descriptionDocumentUser;

    private Timestamp dateTime;

    // User
    private Long idUser;
}


