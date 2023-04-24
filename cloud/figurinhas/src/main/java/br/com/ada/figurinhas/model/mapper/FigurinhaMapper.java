package br.com.ada.figurinhas.model.mapper;

import br.com.ada.figurinhas.model.dto.FigurinhaCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaUpdateDTO;
import br.com.ada.figurinhas.model.entity.Figurinha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FigurinhaMapper {
    FigurinhaDTO parseDTO(Figurinha entity);
    Figurinha parseEntity(FigurinhaDTO dto);
    @Mapping(target = "figurinhaPrototipo.id", source = "figurinhaPrototipoId")
    Figurinha parseEntity(FigurinhaCreationDTO creationDTO);
    @Mapping(target = "figurinhaPrototipo.id", source = "figurinhaPrototipoId")
    Figurinha parseEntity(FigurinhaUpdateDTO updateDTO);
    List<FigurinhaDTO> parseListDTO(List<Figurinha> entities);
    List<Figurinha> parseListEntity(List<FigurinhaCreationDTO> creationDTOList);
}
