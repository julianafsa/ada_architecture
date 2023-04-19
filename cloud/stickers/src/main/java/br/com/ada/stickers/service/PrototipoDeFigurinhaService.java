package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaUpdateDTO;

import java.util.List;
import java.util.Optional;
public interface PrototipoDeFigurinhaService {
    List<PrototipoDeFigurinhaDTO> findAll(Optional<String> opAlbumTemplateId);
    PrototipoDeFigurinhaDTO findById(String id);
    PrototipoDeFigurinhaDTO create(PrototipoDeFigurinhaCreationDTO creationDTO);
    PrototipoDeFigurinhaDTO edit(String id, PrototipoDeFigurinhaUpdateDTO updateDTO);
    void delete(String id);
}
