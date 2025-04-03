package br.com.pesquisa_plus.pesquisa_plus.core.authentication;

// Imports
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.pesquisa_plus.pesquisa_plus.core.authentication.service.TokenService;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseErrorDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import org.springframework.security.authentication.BadCredentialsException;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/token-auth/")
    @Transactional(noRollbackFor = BadCredentialsException.class)
    public ResponseEntity<?> login(@RequestBody AuthenticationModel body) {


        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(body.getEmail(), body.getSenha());
            var auth = authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((UserModel) auth.getPrincipal(), 60);
            var refresh = tokenService.generateToken((UserModel) auth.getPrincipal(), 300000);

            TokenModel tokenAccess = new TokenModel(token, refresh);

            return new ResponseEntity<>(tokenAccess, HttpStatus.OK);
        } catch (Exception e) {
        	throw new  BadCredentialsException("Credenciais inválidas!");
        }
      
    }

    @PostMapping("/token-refresh/")
    public ResponseEntity<?> refresh(@RequestBody RefreshModel refreshBd) {

        UserModel usuario = tokenService.generateRefreshToken(refreshBd.getRefresh());
        if (usuario == null) {
            return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED); 
        }

        // var usernamePassword = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());

        // var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) usuario, 30);
        var refresh = tokenService.generateToken((UserModel) usuario, 3600);

        TokenModel tokenAccess = new TokenModel(token, refresh);

        return new ResponseEntity<TokenModel>(tokenAccess, HttpStatus.OK);
      
    }
    
}