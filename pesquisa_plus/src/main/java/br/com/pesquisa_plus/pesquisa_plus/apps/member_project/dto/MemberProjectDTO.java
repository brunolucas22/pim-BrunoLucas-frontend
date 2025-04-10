package br.com.pesquisa_plus.pesquisa_plus.apps.member_project.dto;

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
// Class MemberProject for the link_project create and update operations
public class MemberProjectDTO {

    private Long id;

    private String roleMemberProject;
	 
    private BigDecimal scholarshipMemberProject;
    
    private Integer[] permissionsMemberProject;

    private Long idSupervisor;

    private Long idProject;

    private Long idUser;
}


