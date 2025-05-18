package br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.controller;

// Imports

import br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.dto.ResourcesProjectDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.models.ResourcesProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.controller.AbstractController;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.PageableFilterDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseListDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

// Annotations for the controller
@RestController
@RequestMapping(value = "/project/{idProject}/resources")
// User access interface
public class ResourcesProjectController extends AbstractController<ResourcesProjectModel, ResourcesProjectDTO, Integer> {
	public ResourcesProjectController(ResourcesProjectModel linkProjectModel, ResourcesProjectDTO linkProjectDTO) {
		super(linkProjectModel, linkProjectDTO);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/listresources")
	public ResponseListDTO listResources(@RequestBody @Valid RequestListDTO requestListDTO, @PathVariable("idProject") Long idProject) {

		this.LOGGER.debug("Requesting all records.");

		requestListDTO.getPageableDTO().getFilters()
		.add(new PageableFilterDTO("idProject", "" 
				+ idProject,
				"EQUAL"));

		return super.list(requestListDTO);
	}
}