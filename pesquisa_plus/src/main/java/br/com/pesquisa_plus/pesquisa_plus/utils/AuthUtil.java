package br.com.pesquisa_plus.pesquisa_plus.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;

public class AuthUtil {

    public static Long getIdUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserModel) {
            return ((UserModel) principal).getId();
        }
        return null;
    }
}