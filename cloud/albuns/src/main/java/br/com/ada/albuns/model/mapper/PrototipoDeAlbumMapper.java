package br.com.ada.albuns.model.mapper;

import br.com.ada.albuns.model.dto.PrototipoDeAlbumDTO;
import br.com.ada.albuns.model.entity.PrototipoDeAlbum;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrototipoDeAlbumMapper {

  PrototipoDeAlbumDTO parseDTO(PrototipoDeAlbum prototipoDeAlbum);

  PrototipoDeAlbum parseEntity(PrototipoDeAlbumDTO prototipoDeAlbumDTO);

  List<PrototipoDeAlbumDTO> parseListDTO(List<PrototipoDeAlbum> prototipoDeAlbum);
  
}
