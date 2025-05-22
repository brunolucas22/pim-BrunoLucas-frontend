package br.com.pesquisa_plus.pesquisa_plus.apps.resources.dto;

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
// Class ResourcesDTO for the resources create and update operations
public class ResourcesDTO {

    // ID of Resources
    private Long id;

    // Name of Resources
    @NotBlank(message = "Nome do recurso é obrigatório!")
    @Size(max = 255, message = "Nome do recurso deve ter no máximo 255 caracteres!")
    private String nameResources;

    // Description of Resources
    @NotBlank(message = "Descrição do recurso é obrigatório!")
    @Size(max = 10000, message = "Descrição do recurso deve ter no máximo 10000 caracteres!")
    private String descriptionResources;

    // Type of Resources
    @NotBlank(message = "Tipo do recurso é obrigatório!")
    @Size(max = 255, message = "Tipo do recurso deve ter no máximo 255 caracteres!")
    private String typeResources;

    // Name of Resources
    @NotBlank(message = "Origem do recurso é obrigatório!")
    @Size(max = 255, message = "Origem do recurso deve ter no máximo 255 caracteres!")
    private String originResources;

    // Value of Resources
    @NotNull(message = "Valor estimado do recurso é obrigatório!")
    private BigDecimal estimatedValueResources;

    // Start Date of Resources
    @NotNull(message = "Data de aquisição do recurso é obrigatório!")
    private LocalDate aquisitionDateResources;

    @NotBlank(message="Status do recurso é obrigatório!")
    private String statusResources;
}


