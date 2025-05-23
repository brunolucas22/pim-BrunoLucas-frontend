package br.com.pesquisa_plus.pesquisa_plus.apps.user.controller;

// Imports
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.pesquisa_plus.pesquisa_plus.shared.controller.AbstractController;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ListPageableDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.PageableDTO;
import jakarta.transaction.Transactional;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.dto.UserDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.repository.UserRepository;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.service.UserService;
import br.com.pesquisa_plus.pesquisa_plus.core.mail.service.EmailService;

import java.io.IOException;
import java.util.Optional;

// import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/user")
// User access interface
public class UserController extends AbstractController<UserModel, UserDTO, Integer> {
	
	@Autowired
	UserService userService;
	
	public UserController(UserModel projectModel, UserDTO projectDTO) {
		super(projectModel, projectDTO);
	}
	
	@PostMapping("/upload")
    public String uploadPhoto(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        return userService.uploadPhoto(id, file);
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
	

	@PutMapping("/updatepassword/{id}")
    public String uploadPhoto(@PathVariable("id") Long id, @RequestBody String password) throws IOException {
        return userService.updatePassword(id, password);
    }

	@PutMapping("/forgotpassword/{cpf:.+}")
	public String forgotPassword(@PathVariable("cpf") String cpf) throws IOException {
		return userService.forgotPassword(cpf);
	}
}