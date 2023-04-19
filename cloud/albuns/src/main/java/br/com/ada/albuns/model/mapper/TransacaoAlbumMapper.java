package br.com.ada.albuns.model.mapper;

import br.com.ada.albuns.model.dto.TransacaoAlbumDTO;
import br.com.ada.albuns.model.entity.TransacaoAlbum;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransacaoAlbumMapper {

  TransacaoAlbumDTO parseDTO(TransacaoAlbum transacaoAlbum);

  List<TransacaoAlbumDTO> parseListDTO(List<TransacaoAlbum> album);
  
}
