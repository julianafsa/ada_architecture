package br.com.ada.figurinhas.model.mapper;

import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoUpdateDTO;
import br.com.ada.figurinhas.model.entity.FigurinhaPrototipo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FigurinhaPrototipoMapper {
    FigurinhaPrototipoDTO parseDTO(FigurinhaPrototipo entity);
    FigurinhaPrototipo parseEntity(FigurinhaPrototipoDTO dto);
    FigurinhaPrototipo parseEntity(FigurinhaPrototipoCreationDTO creationDTO);
    FigurinhaPrototipo parseEntity(FigurinhaPrototipoUpdateDTO updateDTO);
    List<FigurinhaPrototipoDTO> parseListDTO(List<FigurinhaPrototipo> entities);
}
