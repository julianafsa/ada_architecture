package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.StickerToSellCreationDTO;
import br.com.ada.stickers.model.dto.StickerToSellDTO;
import br.com.ada.stickers.model.dto.StickerToSellUpdateDTO;

import java.util.List;

public interface StickerToSellService {

    List<StickerToSellDTO> findAll();
    StickerToSellDTO findById(String id);
    StickerToSellDTO create(StickerToSellCreationDTO creationDTO);
    StickerToSellDTO edit(String id, StickerToSellUpdateDTO updateDTO);
    void delete(String id);
}
