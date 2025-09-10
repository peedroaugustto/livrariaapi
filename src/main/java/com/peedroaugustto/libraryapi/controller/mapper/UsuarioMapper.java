package com.peedroaugustto.libraryapi.controller.mapper;

import com.peedroaugustto.libraryapi.controller.dto.UsuarioDTO;
import com.peedroaugustto.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {

    public abstract Usuario toEntity(UsuarioDTO dto);
}
