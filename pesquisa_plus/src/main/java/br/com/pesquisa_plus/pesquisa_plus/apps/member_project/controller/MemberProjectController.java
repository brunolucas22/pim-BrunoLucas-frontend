package br.com.pesquisa_plus.pesquisa_plus.apps.member_project.controller;

// Imports
import org.springframework.web.bind.annotation.RestController;

import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.dto.MemberProjectDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.dto.MemberProjectView;
import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.models.MemberProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.service.MemberProjectService;
import br.com.pesquisa_plus.pesquisa_plus.shared.controller.AbstractController;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.PageableFilterDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseListDTO;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// Annotations for the controller
@RestController
@RequestMapping(value = "/project/{idProject}/team")
// User access interface
public class MemberProjectController extends AbstractController<MemberProjectModel, MemberProjectDTO, Integer> {
	@Autowired
	private MemberProjectService memberProjectService;
	public MemberProjectController(MemberProjectModel memberProjectModel, MemberProjectDTO memberProjectDTO) {
		super(memberProjectModel, memberProjectDTO);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/listteam")
	public ResponseListDTO listMembers(@RequestBody @Valid RequestListDTO requestListDTO, @PathVariable("idProject") Long idProject) {

		requestListDTO.getPageableDTO().getFilters()
		.add(new PageableFilterDTO("idProject", "" 
				+ idProject,
				"EQUAL"));

		Page<MemberProjectView> listModel = memberProjectService.getAllView(requestListDTO);

		return new ResponseListDTO<MemberProjectView>(listModel.getContent(), listModel.getTotalElements());
	}
}