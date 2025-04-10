package br.com.pesquisa_plus.pesquisa_plus.core.authentication.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.repository.UserRepository;


@Service
public class AuthenticationService implements UserDetailsService {


    @Autowired
    private UserRepository UserRepository;


    @Override
    public UserModel loadUserByUsername(String email) throws UsernameNotFoundException {
        return UserRepository.findByEmailUser(email);
    }
}
