package com.peedroaugustto.libraryapi.controller;

import com.peedroaugustto.libraryapi.controller.dto.UsuarioDTO;
import com.peedroaugustto.libraryapi.controller.mapper.UsuarioMapper;
import com.peedroaugustto.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto) {
        service.salvar(mapper.toEntity(dto));
    }
}
