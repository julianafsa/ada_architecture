package br.com.ada.stickers.model.mapper;

import br.com.ada.stickers.model.dto.StickerJournalCreationDTO;
import br.com.ada.stickers.model.dto.StickerJournalDTO;
import br.com.ada.stickers.model.entity.StickerJournal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StickerJournalMapper {
    StickerJournalDTO parseDTO(StickerJournal entity);
    StickerJournal parseEntity(StickerJournalDTO dto);
    StickerJournal parseEntity(StickerJournalCreationDTO creationDTO);
    List<StickerJournalDTO> parseListDTO(List<StickerJournal> entities);
}
