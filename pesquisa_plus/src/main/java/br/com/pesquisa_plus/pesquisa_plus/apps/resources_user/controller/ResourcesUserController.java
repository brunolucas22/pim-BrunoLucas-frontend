package br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.controller;

// Imports

import br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.dto.ResourcesUserDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.models.ResourcesUserModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.controller.AbstractController;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.PageableFilterDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseListDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

// Annotations for the controller
@RestController
@RequestMapping(value = "/user/{idUser}/resources")
// User access interface
public class ResourcesUserController extends AbstractController<ResourcesUserModel, ResourcesUserDTO, Integer> {
	public ResourcesUserController(ResourcesUserModel linkUserModel, ResourcesUserDTO linkUserDTO) {
		super(linkUserModel, linkUserDTO);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/listresources")
	public ResponseListDTO listResources(@RequestBody @Valid RequestListDTO requestListDTO, @PathVariable("idUser") Long idUser) {

		this.LOGGER.debug("Requesting all records.");

		requestListDTO.getPageableDTO().getFilters()
		.add(new PageableFilterDTO("idUser", "" 
				+ idUser,
				"EQUAL"));

		return super.list(requestListDTO);
	}
}