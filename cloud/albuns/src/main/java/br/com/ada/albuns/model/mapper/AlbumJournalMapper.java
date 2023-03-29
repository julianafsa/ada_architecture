package br.com.ada.albuns.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.ada.albuns.model.dto.AlbumJournalDTO;
import br.com.ada.albuns.model.entity.AlbumJournal;

@Mapper(componentModel = "spring")
public interface AlbumJournalMapper {

  AlbumJournalDTO parseDTO(AlbumJournal albumJournal);

  List<AlbumJournalDTO> parseListDTO(List<AlbumJournal> album);
  
}
