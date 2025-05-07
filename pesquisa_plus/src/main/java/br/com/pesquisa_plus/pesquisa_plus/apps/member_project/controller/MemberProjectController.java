package br.com.pesquisa_plus.pesquisa_plus.apps.member_project.controller;

// Imports
import org.springframework.web.bind.annotation.RestController;

import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.dto.MemberProjectDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.dto.MemberProjectView;
import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.models.MemberProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.service.MemberProjectService;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.dto.UserDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.service.UserService;
import br.com.pesquisa_plus.pesquisa_plus.shared.controller.AbstractController;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.PageableFilterDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	UserService userService;
	
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
	
	@GetMapping("/{id}/photo")
	public ResponseEntity<String> getPhoto(@PathVariable Long id) throws IOException {
	    try {
			return userService.getPhoto(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/search/user/{cpf:.+}")
	public ResponseEntity<UserDTO> getSearchUser(@PathVariable("cpf") String cpf) throws IOException {
		UserDTO userDTO = new UserDTO();
		
		Optional<UserModel> userModel = userService.selectedByCPF(cpf);
		
		if (userModel.isEmpty()) {
        	throw new RequestDataInvalidException("Usuário não encontrado.");
        }
		
		userDTO.setId(userModel.get().getId());
		userDTO.setNameUser(userModel.get().getNameUser());
		userDTO.setEmailUser(userModel.get().getEmailUser());
		
	    return ResponseEntity.ok(userDTO);

		
	}
	
	@GetMapping("/user/{idUser}")
	public ResponseEntity<MemberProjectDTO> getMemberUser(@PathVariable("idUser") Long idUser, @PathVariable("idProject") Long idProject) throws IOException {
		MemberProjectDTO memberProjectDTO = new MemberProjectDTO();
		
		Optional<MemberProjectModel> memberProjectModel = memberProjectService.getByProjectAndUser(idProject, idUser);
		
		if (memberProjectModel.isEmpty()) {
        	throw new RequestDataInvalidException("Usuário não encontrado.");
        }
		
		memberProjectDTO.setId(memberProjectModel.get().getId());
		memberProjectDTO.setIdUser(memberProjectModel.get().getIdUser());
		memberProjectDTO.setPermissionsMemberProject(memberProjectModel.get().getPermissionsMemberProject());
	
		
	    return ResponseEntity.ok(memberProjectDTO);

		
	}
}