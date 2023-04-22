package br.com.ada.stickers.model.mapper;

import br.com.ada.stickers.model.dto.StickerToSellCreationDTO;
import br.com.ada.stickers.model.dto.StickerToSellDTO;
import br.com.ada.stickers.model.dto.StickerToSellUpdateDTO;
import br.com.ada.stickers.model.entity.StickerToSell;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StickerToSellMapper {
    StickerToSellDTO parseDTO(StickerToSell entity);
    StickerToSell parseEntity(StickerToSellDTO dto);
    StickerToSell parseEntity(StickerToSellCreationDTO creationDTO);
    StickerToSell parseEntity(StickerToSellUpdateDTO updateDTO);
    List<StickerToSellDTO> parseListDTO(List<StickerToSell> entities);
}
