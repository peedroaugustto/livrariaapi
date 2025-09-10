package com.peedroaugustto.libraryapi.controller.mapper;

import com.peedroaugustto.libraryapi.controller.dto.AutorDTO;
import com.peedroaugustto.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
