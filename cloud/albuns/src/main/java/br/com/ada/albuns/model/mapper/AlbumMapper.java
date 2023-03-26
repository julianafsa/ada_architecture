package br.com.ada.albuns.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.entity.Album;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

  AlbumDTO parseDTO(Album album);

  Album parseEntity(AlbumDTO albumDTO);

  List<AlbumDTO> parseListDTO(List<Album> album);
  
}
