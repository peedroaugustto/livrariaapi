package com.peedroaugustto.libraryapi.repository;

import com.peedroaugustto.libraryapi.model.Autor;
import com.peedroaugustto.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    List<Livro> findByAutor(Autor autor);
    boolean existsByAutor(Autor autor);

    Optional<Livro> findByIsbn(String isbn);
}
