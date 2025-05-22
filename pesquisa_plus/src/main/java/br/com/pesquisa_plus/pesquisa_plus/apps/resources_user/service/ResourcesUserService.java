package br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.service;

// Imports

import br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.models.ResourcesUserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.repository.ResourcesUserRepository;

import br.com.pesquisa_plus.pesquisa_plus.apps.user.utils.GeneratorPassword;
import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;

import org.springframework.stereotype.Service;

import java.util.Optional;

import br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.models.ResourcesUserModel;

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

    @Override
    // Method that registers the user in the system
    public ResourcesUserModel create(ResourcesUserModel resourcesUser) {

        Optional<ResourcesUserModel> resourceUserModel = resourcesUserRepository.findByIdUserAndIdResource(resourcesUser.getIdUser(),resourcesUser.getIdResource());

        if (!resourceUserModel.isEmpty()) {
            throw new RequestDataInvalidException("Entidade já cadastrada!");
        }

        return super.create(resourcesUser);
    }
 
}
