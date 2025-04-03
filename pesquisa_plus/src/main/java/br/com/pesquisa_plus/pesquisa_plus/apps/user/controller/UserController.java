package br.com.pesquisa_plus.pesquisa_plus.apps.user.controller;

// Imports
import org.springframework.web.bind.annotation.RestController;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ListPageableDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.PageableDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.dto.UserDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.dto.UserListDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.service.UserService;
import br.com.pesquisa_plus.pesquisa_plus.core.mail.service.EmailService;

// import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// Annotations for the controller
@RestController
@RequestMapping(value = "/user")
// User access interface
public class UserController {

    // Add dependencies
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    // POST - Method List of User
    @PostMapping("/")
    private PageableDTO userList(@RequestBody ListPageableDTO dto) {

        // Return the user list service
        return userService.userList(dto.page(), dto.sortField(), dto.sortOrder(), dto.filters());
    }

    // GET - Method Detail of User
    @GetMapping("/{pk}/")
    private ResponseEntity<?> userDetail(@PathVariable Long pk) {

        // emailService.sendEmailText("vidalcaio125@gmail.com", "Usuário Cadastrado com sucesso!",
        //         "Sucesso ao criar seu usuário no sistema!");

        // Return the user detail service
        return userService.userDetail(pk);
    }

    // POST - Method Create of User
    @PostMapping("/create/")
    private ResponseEntity<?> userCreate(@ModelAttribute UserDTO user) {
        // @RequestParam("user_photo") MultipartFile photo

        System.out.println(user.getName_user());

        // Return the user create service
        return userService.userCreate(user);
    }

    // PUT - Método de atualizar usuário
    // @PostMapping("/update/{pk}/")
    // private ResponseEntity<?> usuarioAtualizar(@PathVariable Long pk, @ModelAttribute UsuarioAtualizarDTO usuario) {
    //     // @RequestParam("user_photo") MultipartFile photo

    //     return userService.userUpdate(pk, usuario);
    // }

    // DELETE - Method Delete of User
    @DeleteMapping("/delete/{pk}/")
    private ResponseEntity<?> userDelete(@PathVariable long pk) {

        // Return the user delete service
        return userService.userDelete(pk);
    }

}
