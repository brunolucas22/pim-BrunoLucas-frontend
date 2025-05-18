package br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.service;

// Imports

import br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.models.ResourcesUserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.repository.ResourcesUserRepository;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;
import org.springframework.stereotype.Service;

// Annotations for the service
@Service
// Class of the access interface between business rules and bank queries
public class ResourcesUserService extends AbstractService<ResourcesUserModel, Integer> {

    // Add dependencies
  
    private ResourcesUserRepository resourcesUserRepository;
    // @Autowired
    // private UserPageRepository userPageRepository;
    // private final Validator validator;

    // Constructor
    public ResourcesUserService(ResourcesUserRepository resourcesUserRepository) {
        super();
        this.resourcesUserRepository = resourcesUserRepository;
    }
 
}
