package br.com.ada.stickers.model.mapper;

import br.com.ada.stickers.model.dto.StickerCreationDTO;
import br.com.ada.stickers.model.dto.StickerDTO;
import br.com.ada.stickers.model.dto.StickerUpdateDTO;
import br.com.ada.stickers.model.entity.Sticker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    StickerDTO parseDTO(Sticker entity);
    Sticker parseEntity(StickerDTO dto);
    Sticker parseEntity(StickerCreationDTO creationDTO);
    Sticker parseEntity(StickerUpdateDTO updateDTO);
    List<StickerDTO> parseListDTO(List<Sticker> entities);
}
