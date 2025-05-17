package br.com.pesquisa_plus.pesquisa_plus.apps.documents_user.controller;

// Imports
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.pesquisa_plus.pesquisa_plus.apps.documents_user.dto.DocumentsUserDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.documents_user.models.DocumentsUserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.documents_user.service.DocumentsUserService;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.service.UserService;
import br.com.pesquisa_plus.pesquisa_plus.shared.controller.AbstractController;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.PageableFilterDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseListDTO;
import jakarta.validation.Valid;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Annotations for the controller
@RestController
@RequestMapping(value = "/user/{idUser}/documents")
// User access interface
public class DocumentsUserController extends AbstractController<DocumentsUserModel, DocumentsUserDTO, Integer> {
	
	
	@Autowired
	DocumentsUserService documentsUserService;
	
	
	public DocumentsUserController(DocumentsUserModel documentUserModel, DocumentsUserDTO documentUserDTO) {
		super(documentUserModel, documentUserDTO);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/listdocuments")
	public ResponseListDTO listDocuments(@RequestBody @Valid RequestListDTO requestListDTO, @PathVariable("idUser") Long idUser) {

		requestListDTO.getPageableDTO().getFilters()
		.add(new PageableFilterDTO("idUser", "" 
				+ idUser,
				"EQUAL"));

		return super.list(requestListDTO);
	}
	
	@PostMapping("/upload")
    public String uploadPhoto(@PathVariable("idUser") Long idUser, @RequestParam("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        return documentsUserService.uploadDocument(idUser, id, file);
	}
	
	@GetMapping("/{id}/document")
	public ResponseEntity<String> getPhoto(@PathVariable Long id) throws IOException {
	    try {
			return documentsUserService.getDocument(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}

