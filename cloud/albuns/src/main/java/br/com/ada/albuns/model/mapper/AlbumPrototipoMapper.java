package br.com.ada.albuns.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.ada.albuns.model.dto.AlbumTemplateDTO;
import br.com.ada.albuns.model.entity.AlbumTemplate;

@Mapper(componentModel = "spring")
public interface AlbumTemplateMapper {

  AlbumTemplateDTO parseDTO(AlbumTemplate albumTemplate);

  AlbumTemplate parseEntity(AlbumTemplateDTO albumTemplateDTO);

  List<AlbumTemplateDTO> parseListDTO(List<AlbumTemplate> albumTemplate);
  
}
