package com.peedroaugustto.libraryapi.validator;

import com.peedroaugustto.libraryapi.exceptions.RegistroDuplicadoException;
import com.peedroaugustto.libraryapi.model.Autor;
import com.peedroaugustto.libraryapi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AutorValidator {

    private final AutorRepository repository;

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor ja cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado =
                repository.findByNomeAndDataNascimentoAndNacionalidade
                        (autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

        if(autor.getId() == null){
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
