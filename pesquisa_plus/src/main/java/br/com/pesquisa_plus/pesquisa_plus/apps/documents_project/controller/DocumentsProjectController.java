package br.com.pesquisa_plus.pesquisa_plus.apps.documents_project.controller;

// Imports
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.pesquisa_plus.pesquisa_plus.apps.documents_project.dto.DocumentsProjectDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.documents_project.models.DocumentsProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.documents_project.service.DocumentsProjectService;
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
@RequestMapping(value = "/project/{idProject}/documents")
// User access interface
public class DocumentsProjectController extends AbstractController<DocumentsProjectModel, DocumentsProjectDTO, Integer> {
	
	
	@Autowired
	DocumentsProjectService documentsProjectService;
	
	
	public DocumentsProjectController(DocumentsProjectModel documentProjectModel, DocumentsProjectDTO documentProjectDTO) {
		super(documentProjectModel, documentProjectDTO);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/listdocuments")
	public ResponseListDTO listDocuments(@RequestBody @Valid RequestListDTO requestListDTO, @PathVariable("idProject") Long idProject) {

		requestListDTO.getPageableDTO().getFilters()
		.add(new PageableFilterDTO("idProject", "" 
				+ idProject,
				"EQUAL"));

		return super.list(requestListDTO);
	}
	
	@PostMapping("/upload")
    public String uploadPhoto(@PathVariable("idProject") Long idProject, @RequestParam("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        return documentsProjectService.uploadDocument(idProject, id, file);
	}
	
	@GetMapping("/{id}/document")
	public ResponseEntity<String> getPhoto(@PathVariable Long id) throws IOException {
	    try {
			return documentsProjectService.getDocument(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

