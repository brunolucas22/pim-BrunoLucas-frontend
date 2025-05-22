package br.com.pesquisa_plus.pesquisa_plus.apps.project.service;

import br.com.pesquisa_plus.pesquisa_plus.apps.logging.dto.LogDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.project.dto.ProjectDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import org.springframework.beans.factory.annotation.Autowired;
// Imports
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.models.MemberProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.service.MemberProjectService;
import br.com.pesquisa_plus.pesquisa_plus.apps.project.models.ProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;
import br.com.pesquisa_plus.pesquisa_plus.utils.AuthUtil;
import br.com.pesquisa_plus.pesquisa_plus.apps.project.repository.ProjectRepository;

import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Annotations for the service
@Service
// Class of the access interface between business rules and bank queries
public class ProjectService extends AbstractService<ProjectModel, Integer> {

    // Add dependencies
  
    private ProjectRepository projectRepository;
    
    @Autowired
    private MemberProjectService memberProjectService;
    
    @Autowired
    static AuthUtil authUtil;
    // @Autowired
    // private ProjectPageRepository projectPageRepository;
    // private final Validator validator;

    // Constructor
    public ProjectService(ProjectRepository projectRepository) {
        super();
        this.projectRepository = projectRepository;
    }


    public Page<ProjectDTO> getAllView(RequestListDTO requestListDTO) {

        Page<ProjectDTO> pageLogView = projectRepository.listView(requestListDTO.parserToCriteria(),
                requestListDTO.parserToPageable(), requestListDTO.getPageableDTO().isReport());

        return pageLogView;
    }


    @Override
    public ProjectModel create(ProjectModel project) {
    	
    	Optional<ProjectModel> projectModel = projectRepository.findByNameProject(project.getNameProject());

		if (!projectModel.isEmpty()) {
			throw new RequestDataInvalidException("Entidade já cadastrada!");
		}

        project.setExpectedFinalDateProject(project.getStartDateProject().plusMonths(project.getDurationProject()));
        
   
        ProjectModel projectCreated = super.create(project);
        
        MemberProjectModel member = new MemberProjectModel();
        
        member.setIdSupervisor(Long.parseLong(""+0));
        member.setIdProject(projectCreated.getId());
        member.setPermissionsMemberProject(generatedPermissions(17));
        member.setRoleMemberProject("Criador do Projeto");
        member.setScholarshipMemberProject(BigDecimal.valueOf(0));
        member.setIdUser(authUtil.getIdUsuarioLogado());
        
        memberProjectService.create(member);
        
        return projectCreated;
    }
    
    @Override
    // Method to update an existing project in the system
    public ProjectModel update(ProjectModel project) {

        
    	Optional<ProjectModel> projectModel = projectRepository.findByNameProject(project.getNameProject());

		if (!projectModel.isEmpty()
				&& project.getId() != projectModel.get().getId()) {
			throw new RequestDataInvalidException("Entidade já cadastrada!");
		}
		
		project.setExpectedFinalDateProject(project.getStartDateProject().plusMonths(project.getDurationProject()));
		
		return super.update(project);
    }
    
    public static Integer[] generatedPermissions(int lastPermission) {
        Integer[] numbers = new Integer[lastPermission];
        for (int i = 0; i < lastPermission; i++) {
        	numbers[i] = i + 1;
        }
        return numbers;
    }

}
