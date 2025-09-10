package com.peedroaugustto.libraryapi.repository;

import com.peedroaugustto.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));
        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("086b70d2-b758-4493-8af7-f0c8bc8ae9da");
        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(autorEncontrado);
            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));
            autorRepository.save(autorEncontrado);
        }

    }

    @Test
    public void listarTest(){
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);

    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + autorRepository.count());

    }
}
