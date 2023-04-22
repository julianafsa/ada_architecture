package br.com.ada.figurinhas.service;

import br.com.ada.figurinhas.model.dto.FigurinhaJournalCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaJournalDTO;

import java.util.List;
public interface FigurinhaJournalService {
    List<FigurinhaJournalDTO> findAll();
    FigurinhaJournalDTO findById(String id);
    FigurinhaJournalDTO create(FigurinhaJournalCreationDTO creationDTO);
}
