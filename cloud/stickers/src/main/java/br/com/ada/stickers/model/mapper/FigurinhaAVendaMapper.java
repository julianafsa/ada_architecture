package br.com.ada.stickers.model.mapper;

import br.com.ada.stickers.model.dto.FigurinhaAVendaCreationDTO;
import br.com.ada.stickers.model.dto.FigurinhaAVendaDTO;
import br.com.ada.stickers.model.dto.FigurinhaAVendaUpdateDTO;
import br.com.ada.stickers.model.entity.FigurinhaAVenda;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FigurinhaAVendaMapper {
    FigurinhaAVendaDTO parseDTO(FigurinhaAVenda entity);
    FigurinhaAVenda parseEntity(FigurinhaAVendaDTO dto);
    FigurinhaAVenda parseEntity(FigurinhaAVendaCreationDTO creationDTO);
    FigurinhaAVenda parseEntity(FigurinhaAVendaUpdateDTO updateDTO);
    List<FigurinhaAVendaDTO> parseListDTO(List<FigurinhaAVenda> entities);
}
