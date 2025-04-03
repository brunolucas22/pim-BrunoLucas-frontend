package br.com.pesquisa_plus.pesquisa_plus.apps.link_project.service;

// Imports
import org.springframework.stereotype.Service;
import br.com.pesquisa_plus.pesquisa_plus.apps.link_project.models.LinkProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;

import br.com.pesquisa_plus.pesquisa_plus.apps.link_project.repository.LinkProjectRepository;

import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;
import java.util.Optional;

// Annotations for the service
@Service
// Class of the access interface between business rules and bank queries
public class LinkProjectService extends AbstractService<LinkProjectModel, Integer> {

    // Add dependencies
  
    private LinkProjectRepository linkProjectRepository;
    // @Autowired
    // private ProjectPageRepository projectPageRepository;
    // private final Validator validator;

    // Constructor
    public LinkProjectService(LinkProjectRepository linkProjectRepository) {
        super();
        this.linkProjectRepository = linkProjectRepository;
    }
 
}
