package br.com.pesquisa_plus.pesquisa_plus.apps.user.controller;

// Imports

import br.com.pesquisa_plus.pesquisa_plus.apps.user.dto.UserDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.service.UserService;
import br.com.pesquisa_plus.pesquisa_plus.shared.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/useranonymous")
// User access interface
public class UserControllerAnonymous extends AbstractController<UserModel, UserDTO, Integer> {

	@Autowired
	UserService userService;

	public UserControllerAnonymous(UserModel projectModel, UserDTO projectDTO) {
		super(projectModel, projectDTO);
	}


	@PostMapping("/upload")
	public String uploadPhoto(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
		return userService.uploadPhoto(id, file);
	}


	@PutMapping("/forgotpassword/{cpf:.+}")
	public String forgotPassword(@PathVariable("cpf") String cpf) throws IOException {
		return userService.forgotPassword(cpf);
	}
}