package br.com.ada.stickers.model.mapper;

import br.com.ada.stickers.model.dto.TransacaoFigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.TransacaoFigurinhaDTO;
import br.com.ada.stickers.model.entity.TransacaoFigurinha;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransacaoFigurinhaMapper {
    TransacaoFigurinhaDTO parseDTO(TransacaoFigurinha entity);
    TransacaoFigurinha parseEntity(TransacaoFigurinhaDTO dto);
    TransacaoFigurinha parseEntity(TransacaoFigurinhaCreationDTO creationDTO);
    List<TransacaoFigurinhaDTO> parseListDTO(List<TransacaoFigurinha> entities);
}
