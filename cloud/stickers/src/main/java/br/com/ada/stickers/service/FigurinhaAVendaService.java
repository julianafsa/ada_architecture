package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.FigurinhaAVendaDTO;
import br.com.ada.stickers.model.dto.FigurinhaAVendaCreationDTO;
import br.com.ada.stickers.model.dto.FigurinhaAVendaUpdateDTO;
import br.com.ada.stickers.model.entity.FigurinhaAVenda;

import java.util.List;
import java.util.Optional;

public interface FigurinhaAVendaService {

    List<FigurinhaAVendaDTO> findAll();
    FigurinhaAVenda findById(String id);
    FigurinhaAVendaDTO create(FigurinhaAVendaCreationDTO creationDTO);
    FigurinhaAVendaDTO edit(String id, FigurinhaAVendaUpdateDTO updateDTO);
    void delete(String id);
    void deleteByStickerId(String stickerId);
    Optional<FigurinhaAVenda> findByStickerId(String stickerId);
}
