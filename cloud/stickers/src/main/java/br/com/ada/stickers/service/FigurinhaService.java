package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.CreateFigurinhaMessage;
import br.com.ada.stickers.model.dto.FigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.FigurinhaDTO;
import br.com.ada.stickers.model.dto.FigurinhaUpdateDTO;
import br.com.ada.stickers.model.entity.Figurinha;

import java.util.List;

public interface FigurinhaService {

    List<FigurinhaDTO> findAll();
    FigurinhaDTO findById(String id);
    FigurinhaDTO create(FigurinhaCreationDTO creationDTO);
    Figurinha edit(String id, FigurinhaUpdateDTO updateDTO);
    void delete(String id);
    List<Figurinha> findByAlbumId(String albumId);
    List<Figurinha> editAll(List<Figurinha> entities);
    boolean createStickersForAlbum(CreateFigurinhaMessage createFigurinhaMessage);
}
