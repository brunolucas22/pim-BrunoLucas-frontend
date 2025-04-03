package br.com.pesquisa_plus.pesquisa_plus.apps.link_project.controller;

// Imports
import org.springframework.web.bind.annotation.RestController;

import br.com.pesquisa_plus.pesquisa_plus.apps.link_project.dto.LinkProjectDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.link_project.models.LinkProjectModel;

import br.com.pesquisa_plus.pesquisa_plus.shared.controller.AbstractController;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.PageableFilterDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseListDTO;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// Annotations for the controller
@RestController
@RequestMapping(value = "/project/{idProject}/links")
// User access interface
public class LinkProjectController extends AbstractController<LinkProjectModel, LinkProjectDTO, Integer> {
	public LinkProjectController(LinkProjectModel linkProjectModel, LinkProjectDTO linkProjectDTO) {
		super(linkProjectModel, linkProjectDTO);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/listlinks")
	public ResponseListDTO listLinks(@RequestBody @Valid RequestListDTO requestListDTO, @PathVariable("idProject") Long idProject) {

		this.LOGGER.debug("Requesting all records.");

		requestListDTO.getPageableDTO().getFilters()
		.add(new PageableFilterDTO("idProject", "" 
				+ idProject,
				"EQUAL"));

		return super.list(requestListDTO);
	}
}