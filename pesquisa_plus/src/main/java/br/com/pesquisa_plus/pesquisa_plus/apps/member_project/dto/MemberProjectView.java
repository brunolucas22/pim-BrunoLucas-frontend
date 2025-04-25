package br.com.pesquisa_plus.pesquisa_plus.apps.member_project.dto;

// Imports
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import org.springframework.stereotype.Component;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component

public class MemberProjectView {

    private Long id;

    private String roleMemberProject;
	 
    private BigDecimal scholarshipMemberProject;
    
    private Integer[] permissionsMemberProject;

    private Long idSupervisor;

    private String user;
    
    private boolean havePhoto;
    
    private Long idUser;
}


