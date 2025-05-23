package br.com.pesquisa_plus.pesquisa_plus.apps.user.service;

// Imports
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;


import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;

import jakarta.transaction.Transactional;

import org.springframework.core.io.Resource;


import java.nio.file.Path;

import br.com.pesquisa_plus.pesquisa_plus.apps.user.dto.UserDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.repository.UserRepository;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.utils.GeneratorPassword;
import br.com.pesquisa_plus.pesquisa_plus.core.mail.service.EmailService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

// Annotations for the service
@Service
@Validated
// Class of the access interface between business rules and bank queries
public class UserService extends AbstractService<UserModel, Integer> {

    // Add dependencies
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private static final String UPLOAD_DIR = "uploads/";
    
    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    // Method that registers the user in the system
    public UserModel create(UserModel user) {

    	Optional<UserModel> userModelByCpf = userRepository.findByCpfUser(user.getCpfUser());
    			
    	Optional<UserModel> userModelByEmail = 	Optional.ofNullable(userRepository.findByEmailUser(user.getEmailUser()));

		if (!userModelByCpf.isEmpty() || !userModelByEmail.isEmpty()) {
			throw new RequestDataInvalidException("Entidade já cadastrada!");
		}
       
        String password = GeneratorPassword.generate(8);
        user.setPasswordUser(passwordEncoder.encode(password));
        
        emailService.sendEmailCreatedUser(password, user);
        
     
        return super.create(user);
    }
   
    @Transactional
    public String uploadPhoto(Long id, MultipartFile file) throws IOException {
    	
    	Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isEmpty()) {
        	throw new RequestDataInvalidException("Usuário não encontrado.");
        }

        UserModel user = userModel.get();

        // Criar o diretório se não existir
        Files.createDirectories(Paths.get(UPLOAD_DIR));

        // Gerar um nome único para a imagem
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        // Remover a foto antiga, se existir
        if (user.getPhotoUser() != null) {
            Path oldFilePath = Paths.get(user.getPhotoUser());
            Files.deleteIfExists(oldFilePath);
        }

        // Salvar a nova imagem no diretório
        Files.write(filePath, file.getBytes());

        // Atualizar o caminho da imagem no banco de dados
       
        user.setPhotoUser(filePath.toString());
        
       super.update(user);
       
       return "";
        
    }
    
    public ResponseEntity<String> getPhoto(Long id) throws IOException{
    	Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isEmpty()) {
            return ResponseEntity.ok("");
        }
        if(userModel.get().getPhotoUser() == null) {
            return ResponseEntity.ok("");
        }

	    Path path = Paths.get(userModel.get().getPhotoUser());
	    if (!Files.exists(path)) {
            return ResponseEntity.ok("");
	    }

	    try {
	        String base64Image = getImageAsBase64(userModel.get().getPhotoUser());
	        return ResponseEntity.ok(base64Image);
	    } catch (IOException e) {
	    	return ResponseEntity.ok("");
	    }
    }
    
    public String getImageAsBase64(String filePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        String mimeType = Files.probeContentType(Paths.get(filePath));

        if (mimeType == null) {
            mimeType = "application/octet-stream"; // Fallback caso não detecte
        }

        return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(fileContent);
    }
    
    @Override
    public UserModel update(UserModel user) {
    	

    	Optional<UserModel> userModelByCpf = userRepository.findByCpfUser(user.getCpfUser());
		
    	Optional<UserModel> userModelByEmail = 	Optional.ofNullable(userRepository.findByEmailUser(user.getEmailUser()));

		if ((!userModelByCpf.isEmpty() || !userModelByEmail.isEmpty()) && (user.getId() != userModelByCpf.get().getId() || user.getId() != userModelByEmail.get().getId())) {
			throw new RequestDataInvalidException("Entidade já cadastrada!");
		}
		
		Optional<UserModel> userModel = userRepository.findById(user.getId());
        if (userModel.isEmpty()) {
        	throw new RequestDataInvalidException("Usuário não encontrado.");
        }

		
        UserModel userUpdate = new UserModel();
        
        userUpdate.setId(user.getId());
        userUpdate.setCpfUser(user.getCpfUser());
        userUpdate.setEmailUser(user.getEmailUser());
        userUpdate.setNameUser(user.getNameUser());
        userUpdate.setPasswordUser(userModel.get().getPasswordUser());
        userUpdate.setPhoneUser(user.getPhoneUser());
        userUpdate.setStatusUser(user.getStatusUser());
        userUpdate.setTypeUser(user.getTypeUser());
        userUpdate.setPhotoUser(user.getPhotoUser());
        userUpdate.setComplementaryDataUser(user.getComplementaryDataUser());
        if (user.getPasswordUser() != null && !user.getPasswordUser().trim().isEmpty()) {
            
        	System.out.println("isso é masterchaf");
        	userUpdate.setPasswordUser(passwordEncoder.encode(user.getPasswordUser()));}
        
       return super.update(userUpdate);
    }
    
    @Transactional
    public String updatePassword( Long id, String password) {
		
		Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isEmpty()) {
        	throw new RequestDataInvalidException("Usuário não encontrado.");
        }
       userModel.get().setPasswordUser(passwordEncoder.encode(password.replace("\"", "")));
       super.update(userModel.get());
       return "";
    }

    public Optional<UserModel> selectedByCPF(String cpf) {

    	return userRepository.findByCpfUser(cpf);
		
	
	}

    @Override
	@Transactional
	public void deleteByID(Integer id) {
	    Optional<UserModel> userModel = userRepository.findById(id);
	    
	    if (userModel.isEmpty()) {
	        throw new RequestDataInvalidException("Usuário não encontrado.");
	    }
	
	    UserModel user = userModel.get();
	
	    // Deleta a foto se existir
	    if (user.getPhotoUser() != null) {
	        try {
	            Path photoPath = Paths.get(user.getPhotoUser());
	            Files.deleteIfExists(photoPath);
	        } catch (IOException e) {
	            // Você pode logar isso ou apenas seguir com a exclusão
	            System.err.println("Erro ao deletar a foto do usuário: " + e.getMessage());
	        }
	    }
	
	    // Deleta o usuário do banco
	    super.deleteByID(id);
	}

    @Transactional
    // Method that registers the user in the system
    public String forgotPassword(String cpf) {

        Optional<UserModel> userModelByCpf = userRepository.findByCpfUser(cpf);


        if (userModelByCpf.isEmpty()) {
            throw new RequestDataInvalidException("Usuário não encontrado.");
        }

        String password = GeneratorPassword.generate(8);
        userModelByCpf.get().setPasswordUser(passwordEncoder.encode(password));

        emailService.sendEmailForgotPassword(password, userModelByCpf.get());


        super.update(userModelByCpf.get());

        return "";
    }
   
}
