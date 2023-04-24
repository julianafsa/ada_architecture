package br.com.ada.figurinhas.service;

import br.com.ada.figurinhas.model.dto.FigurinhaToSellCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaToSellDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaToSellUpdateDTO;
import br.com.ada.figurinhas.model.entity.FigurinhaToSell;

import java.util.List;
import java.util.Optional;

public interface FigurinhaToSellService {

    List<FigurinhaToSellDTO> findAll();
    FigurinhaToSell findById(String id);
    FigurinhaToSellDTO create(FigurinhaToSellCreationDTO creationDTO);
    FigurinhaToSellDTO edit(String id, FigurinhaToSellUpdateDTO updateDTO);
    void delete(String id);
    void deleteByFigurinhaId(String figurinhaId);
    Optional<FigurinhaToSell> findByFigurinhaId(String figurinhaId);
}
