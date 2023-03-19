package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.StickerCreationDTO;
import br.com.ada.stickers.model.dto.StickerDTO;
import br.com.ada.stickers.model.dto.StickerUpdateDTO;

import java.util.List;

public interface StickerService {

    List<StickerDTO> findAll();
    StickerDTO findById(Long id);
    StickerDTO create(StickerCreationDTO creationDTO);
    StickerDTO edit(Long id, StickerUpdateDTO updateDTO);
    void delete(Long id);
}
