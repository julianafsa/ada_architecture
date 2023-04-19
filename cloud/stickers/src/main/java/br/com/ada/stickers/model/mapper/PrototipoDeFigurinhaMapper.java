package br.com.ada.stickers.model.mapper;

import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaUpdateDTO;
import br.com.ada.stickers.model.entity.PrototipoDeFigurinha;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrototipoDeFigurinhaMapper {
    PrototipoDeFigurinhaDTO parseDTO(PrototipoDeFigurinha entity);
    PrototipoDeFigurinha parseEntity(PrototipoDeFigurinhaDTO dto);
    PrototipoDeFigurinha parseEntity(PrototipoDeFigurinhaCreationDTO creationDTO);
    PrototipoDeFigurinha parseEntity(PrototipoDeFigurinhaUpdateDTO updateDTO);
    List<PrototipoDeFigurinhaDTO> parseListDTO(List<PrototipoDeFigurinha> entities);
}
