package br.com.pesquisa_plus.pesquisa_plus.apps.project.service;

// Imports
import org.springframework.stereotype.Service;
import br.com.pesquisa_plus.pesquisa_plus.apps.project.models.ProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;

import br.com.pesquisa_plus.pesquisa_plus.apps.project.repository.ProjectRepository;

import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;
import java.util.Optional;

// Annotations for the service
@Service
// Class of the access interface between business rules and bank queries
public class ProjectService extends AbstractService<ProjectModel, Integer> {

    // Add dependencies
  
    private ProjectRepository projectRepository;
    // @Autowired
    // private ProjectPageRepository projectPageRepository;
    // private final Validator validator;

    // Constructor
    public ProjectService(ProjectRepository projectRepository) {
        super();
        this.projectRepository = projectRepository;
    }

    
    @Override
    public ProjectModel create(ProjectModel project) {
    	
    	Optional<ProjectModel> projectModel = projectRepository.findByNameProject(project.getNameProject());

		if (!projectModel.isEmpty()) {
			throw new RequestDataInvalidException("Entidade já cadastrada!");
		}

        project.setExpectedFinalDateProject(project.getStartDateProject().plusMonths(project.getDurationProject()));
        
   
        return super.create(project);
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

}
