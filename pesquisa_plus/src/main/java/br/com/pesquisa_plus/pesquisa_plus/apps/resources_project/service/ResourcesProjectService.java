package br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.service;

// Imports

import br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.models.ResourcesProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.repository.ResourcesProjectRepository;

import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;

import java.util.Optional;

import org.springframework.stereotype.Service;

// Annotations for the service
@Service
// Class of the access interface between business rules and bank queries
public class ResourcesProjectService extends AbstractService<ResourcesProjectModel, Integer> {

    // Add dependencies
  
    private ResourcesProjectRepository resourcesProjectRepository;
    // @Autowired
    // private ProjectPageRepository projectPageRepository;
    // private final Validator validator;

    // Constructor
    public ResourcesProjectService(ResourcesProjectRepository resourcesProjectRepository) {
        super();
        this.resourcesProjectRepository = resourcesProjectRepository;
    }

      @Override
    // Method that registers the user in the system
    public ResourcesProjectModel create(ResourcesProjectModel resourcesProject) {

        Optional<ResourcesProjectModel> resourceProjectModel = resourcesProjectRepository.findByIdProjectAndIdResource(resourcesProject.getIdProject(),resourcesProject.getIdResource());

        if (!resourceProjectModel.isEmpty()) {
            throw new RequestDataInvalidException("Entidade já cadastrada!");
        }

        return super.create(resourcesProject);
    }
 
}
