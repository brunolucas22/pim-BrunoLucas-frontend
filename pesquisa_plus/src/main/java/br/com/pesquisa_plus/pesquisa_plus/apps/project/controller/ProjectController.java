package br.com.pesquisa_plus.pesquisa_plus.apps.project.controller;

// Imports
import org.springframework.web.bind.annotation.RestController;

import br.com.pesquisa_plus.pesquisa_plus.apps.project.dto.ProjectDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.project.models.ProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.controller.AbstractController;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseListDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// Annotations for the controller
@RestController
@RequestMapping(value = "/project")
// User access interface
public class ProjectController extends AbstractController<ProjectModel, ProjectDTO, Integer> {
	public ProjectController(ProjectModel projectModel, ProjectDTO projectDTO) {
		super(projectModel, projectDTO);
	}
	
}