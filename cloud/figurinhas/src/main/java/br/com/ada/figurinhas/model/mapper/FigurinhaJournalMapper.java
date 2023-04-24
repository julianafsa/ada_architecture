package br.com.ada.figurinhas.model.mapper;

import br.com.ada.figurinhas.model.dto.FigurinhaJournalCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaJournalDTO;
import br.com.ada.figurinhas.model.entity.FigurinhaJournal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FigurinhaJournalMapper {
    FigurinhaJournalDTO parseDTO(FigurinhaJournal entity);
    FigurinhaJournal parseEntity(FigurinhaJournalDTO dto);
    FigurinhaJournal parseEntity(FigurinhaJournalCreationDTO creationDTO);
    List<FigurinhaJournalDTO> parseListDTO(List<FigurinhaJournal> entities);
}
