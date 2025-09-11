package com.peedroaugustto.libraryapi.security;

import com.peedroaugustto.libraryapi.model.Usuario;
import com.peedroaugustto.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof CustomAuthentication customAuthentication){
            return customAuthentication.getUsuario();
        }

        return null;
    }

}
