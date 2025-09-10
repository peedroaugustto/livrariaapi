package com.peedroaugustto.libraryapi.validator;

import com.peedroaugustto.libraryapi.exceptions.CampoInvalidoException;
import com.peedroaugustto.libraryapi.exceptions.RegistroDuplicadoException;
import com.peedroaugustto.libraryapi.model.Livro;
import com.peedroaugustto.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro){
        if (existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado!");
        }

        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de publicacao a partir de 2020, o preço é obrigatório.");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());
        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado.map(Livro::getId)
                .stream().anyMatch(id -> !id.equals(livro.getId()));
    }
}
