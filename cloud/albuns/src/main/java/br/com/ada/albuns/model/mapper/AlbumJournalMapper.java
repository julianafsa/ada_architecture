package br.com.ada.albuns.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.ada.albuns.model.dto.AlbumJournalDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.entity.AlbumJournal;

@Mapper(componentModel = "spring")
public interface AlbumJournalMapper {

  AlbumJournalDTO parseDTO(AlbumJournal albumJournal);

  Album parseEntity(AlbumJournalDTO albumJournalDTO);

  List<AlbumJournalDTO> parseListDTO(List<AlbumJournal> album);
  
}
