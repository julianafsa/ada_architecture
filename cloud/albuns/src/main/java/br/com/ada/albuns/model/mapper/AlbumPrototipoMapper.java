package br.com.ada.albuns.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.ada.albuns.model.dto.AlbumPrototipoDTO;
import br.com.ada.albuns.model.entity.AlbumPrototipo;

@Mapper(componentModel = "spring")
public interface AlbumPrototipoMapper {

  AlbumPrototipoDTO parseDTO(AlbumPrototipo albumPrototipo);

  AlbumPrototipo parseEntity(AlbumPrototipoDTO albumPrototipoDTO);

  List<AlbumPrototipoDTO> parseListDTO(List<AlbumPrototipo> albumPrototipo);
  
}
