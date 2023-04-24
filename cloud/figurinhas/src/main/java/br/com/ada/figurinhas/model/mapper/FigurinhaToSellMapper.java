package br.com.ada.figurinhas.model.mapper;

import br.com.ada.figurinhas.model.dto.FigurinhaToSellCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaToSellDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaToSellUpdateDTO;
import br.com.ada.figurinhas.model.entity.FigurinhaToSell;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FigurinhaToSellMapper {
    FigurinhaToSellDTO parseDTO(FigurinhaToSell entity);
    FigurinhaToSell parseEntity(FigurinhaToSellDTO dto);
    FigurinhaToSell parseEntity(FigurinhaToSellCreationDTO creationDTO);
    FigurinhaToSell parseEntity(FigurinhaToSellUpdateDTO updateDTO);
    List<FigurinhaToSellDTO> parseListDTO(List<FigurinhaToSell> entities);
}
