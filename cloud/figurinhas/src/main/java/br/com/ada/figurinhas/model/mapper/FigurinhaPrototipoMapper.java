package br.com.ada.stickers.model.mapper;

import br.com.ada.stickers.model.dto.StickerTemplateCreationDTO;
import br.com.ada.stickers.model.dto.StickerTemplateDTO;
import br.com.ada.stickers.model.dto.StickerTemplateUpdateDTO;
import br.com.ada.stickers.model.entity.StickerTemplate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StickerTemplateMapper {
    StickerTemplateDTO parseDTO(StickerTemplate entity);
    StickerTemplate parseEntity(StickerTemplateDTO dto);
    StickerTemplate parseEntity(StickerTemplateCreationDTO creationDTO);
    StickerTemplate parseEntity(StickerTemplateUpdateDTO updateDTO);
    List<StickerTemplateDTO> parseListDTO(List<StickerTemplate> entities);
}
