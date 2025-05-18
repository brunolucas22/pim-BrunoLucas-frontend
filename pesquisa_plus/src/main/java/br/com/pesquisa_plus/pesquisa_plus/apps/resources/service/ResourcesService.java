package br.com.pesquisa_plus.pesquisa_plus.apps.resources.service;

import org.springframework.beans.factory.annotation.Autowired;
// Imports
import org.springframework.stereotype.Service;

import br.com.pesquisa_plus.pesquisa_plus.apps.resources.models.ResourcesModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;
import br.com.pesquisa_plus.pesquisa_plus.utils.AuthUtil;
import br.com.pesquisa_plus.pesquisa_plus.apps.resources.repository.ResourcesRepository;

import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Annotations for the service
@Service
// Class of the access interface between business rules and bank queries
public class ResourcesService extends AbstractService<ResourcesModel, Integer> {

    private ResourcesRepository resourcesRepository;
   
    // Constructor
    public ResourcesService(ResourcesRepository resourcesRepository) {
        super();
        this.resourcesRepository = resourcesRepository;
    }

}
