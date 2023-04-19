package br.com.ada.stickers.model.mapper;

import br.com.ada.stickers.model.dto.FigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.FigurinhaUpdateDTO;
import br.com.ada.stickers.model.dto.FigurinhaDTO;
import br.com.ada.stickers.model.entity.Figurinha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FigurinhaMapper {
    FigurinhaDTO parseDTO(Figurinha entity);
    Figurinha parseEntity(FigurinhaDTO dto);
    @Mapping(target = "stickerTemplate.id", source = "stickerTemplateId")
    Figurinha parseEntity(FigurinhaCreationDTO creationDTO);
    @Mapping(target = "stickerTemplate.id", source = "stickerTemplateId")
    Figurinha parseEntity(FigurinhaUpdateDTO updateDTO);
    List<FigurinhaDTO> parseListDTO(List<Figurinha> entities);
    List<Figurinha> parseListEntity(List<FigurinhaCreationDTO> creationDTOList);
}
