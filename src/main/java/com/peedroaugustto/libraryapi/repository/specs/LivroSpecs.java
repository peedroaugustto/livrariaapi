package com.peedroaugustto.libraryapi.repository.specs;

import com.peedroaugustto.libraryapi.model.GeneroLivro;
import com.peedroaugustto.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return (root, cq, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo){
        return (root, cq, cb) -> cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%" );
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero){
        return (root, cq, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
        return (root, cq, cb) -> cb.equal(cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }

    public static Specification<Livro> autorLike(String nome){
        return (root, cq, cb) -> {
            return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }
}
