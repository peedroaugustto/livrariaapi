package com.peedroaugustto.libraryapi.security;

import com.peedroaugustto.libraryapi.model.Usuario;
import com.peedroaugustto.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = service.obterPorLogin(username);

        if(usuario == null){
            throw new UsernameNotFoundException("Usuario nao encontrado!");
        }

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}
