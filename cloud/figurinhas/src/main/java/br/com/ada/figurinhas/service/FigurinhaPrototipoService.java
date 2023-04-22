package br.com.ada.figurinhas.service;

import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoUpdateDTO;

import java.util.List;
import java.util.Optional;
public interface FigurinhaPrototipoService {
    List<FigurinhaPrototipoDTO> findAll(Optional<String> opAlbumPrototipoId);
    FigurinhaPrototipoDTO findById(String id);
    FigurinhaPrototipoDTO create(FigurinhaPrototipoCreationDTO creationDTO);
    FigurinhaPrototipoDTO edit(String id, FigurinhaPrototipoUpdateDTO updateDTO);
    void delete(String id);
}
