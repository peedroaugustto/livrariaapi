package com.peedroaugustto.libraryapi.repository;


import com.peedroaugustto.libraryapi.model.Autor;
import com.peedroaugustto.libraryapi.model.GeneroLivro;
import com.peedroaugustto.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
class LivroRepositoryTest {
    
    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;


    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository.findAll().stream().findFirst().get();
        livro.setAutor(autor);
        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Jorge");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1961, 5, 13));

        livro.setAutor(autor);
        repository.save(livro);
    }

}