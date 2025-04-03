package br.com.pesquisa_plus.pesquisa_plus.apps.project.dto;

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
// Class ProjectDTO for the project create and update operations
public class ProjectDTO {

    // ID of Project
    private Long id;

    // Name of Project
    @NotBlank(message = "Nome do projeto é obrigatório!")
    @Size(max = 255, message = "Nome do projeto deve ter no máximo 255 caracteres!")
    private String nameProject;

    // Value of Project
    @NotNull(message = "Valor do projeto é obrigatório!")
    @Min(value = 0, message = "Valor mínimo da é R$ 0,01!")
    private BigDecimal valueProject;

    // Duration of Project
    @NotNull(message = "Duração do projeto é obrigatório!")
    @Min(value = 0, message = "Valor mínimo da duração é 1!")
    private Integer durationProject;

    // Description of Project
    @NotBlank(message = "Descrição do projeto é obrigatório!")
    @Size(max = 1000, message = "Descrição do projeto deve ter no máximo 1000 caracteres!")
    private String descriptionProject;

    // Start Date of Project
    @NotNull(message = "Data de início do projeto é obrigatório!")
    private LocalDate startDateProject;

    // Final Date of Project
    private LocalDate finalDateProject;
    
    private LocalDate expectedFinalDateProject;
}


