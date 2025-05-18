package br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.service;

// Imports

import br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.models.ResourcesProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.repository.ResourcesProjectRepository;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;
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
 
}
