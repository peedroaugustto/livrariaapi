package com.peedroaugustto.libraryapi.controller;


import com.peedroaugustto.libraryapi.controller.dto.AutorDTO;
import com.peedroaugustto.libraryapi.controller.mapper.AutorMapper;
import com.peedroaugustto.libraryapi.model.Autor;
import com.peedroaugustto.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<?> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);
        return service.obterPorId(idAutor).map(it -> {
            AutorDTO dto = mapper.toDTO(it);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(required = false) String nome,
                                                    @RequestParam(required = false) String nacionalidade) {


        List<AutorDTO> autorList = service.pesquisaByExample(nome, nacionalidade)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(autorList);

    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody AutorDTO autor) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Autor autorAtualizar = autorOptional.get();
        autorAtualizar.setNome(autor.nome());
        autorAtualizar.setDataNascimento(autor.dataNascimento());
        autorAtualizar.setNacionalidade(autor.nacionalidade());

        service.atualizar(autorAtualizar);

        return ResponseEntity.noContent().build();
    }

}
