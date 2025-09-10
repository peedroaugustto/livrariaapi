package com.peedroaugustto.libraryapi.controller;


import com.peedroaugustto.libraryapi.controller.dto.CadastroLivroDTO;
import com.peedroaugustto.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.peedroaugustto.libraryapi.controller.mapper.LivroMapper;
import com.peedroaugustto.libraryapi.model.GeneroLivro;
import com.peedroaugustto.libraryapi.model.Livro;
import com.peedroaugustto.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        URI url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable String id){
        return service.obterPorId(UUID.fromString(id)).map(livro -> {
            var dto = mapper.toDto(livro);
            return ResponseEntity.ok(dto);
        }).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable String id){
        return service.obterPorId(UUID.fromString(id)).map( livro -> {
            service.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(()->ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(@RequestParam(required = false)
                                                                    String isbn,
                                                                    @RequestParam(required = false)
                                                                    String titulo,
                                                                    @RequestParam(required = false)
                                                                    String nomeAutor,
                                                                    @RequestParam(required = false)
                                                                    GeneroLivro genero,
                                                                    @RequestParam(required = false)
                                                                    Integer anoPublicacao,
                                                                    @RequestParam(defaultValue = "0")
                                                                    Integer pagina,
                                                                    @RequestParam(defaultValue = "10")
                                                                    Integer tamanhoPagina) {
        Page<Livro> pesquisa = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
        Page<ResultadoPesquisaLivroDTO> map = pesquisa.map(mapper::toDto);
        return ResponseEntity.ok(map);

    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody @Valid CadastroLivroDTO dto){
        return service.obterPorId(UUID.fromString(id)).map(livro -> {
            Livro entidadeAux = mapper.toEntity(dto);
            livro.setDataPublicacao(entidadeAux.getDataPublicacao());
            livro.setIsbn(entidadeAux.getIsbn());
            livro.setPreco(entidadeAux.getPreco());
            livro.setGenero(entidadeAux.getGenero());
            livro.setTitulo(entidadeAux.getTitulo());
            livro.setAutor(entidadeAux.getAutor());
            service.atualizar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(()->ResponseEntity.notFound().build());

    }
}
