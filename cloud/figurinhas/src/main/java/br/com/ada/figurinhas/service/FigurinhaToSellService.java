package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.StickerToSellCreationDTO;
import br.com.ada.stickers.model.dto.StickerToSellDTO;
import br.com.ada.stickers.model.dto.StickerToSellUpdateDTO;
import br.com.ada.stickers.model.entity.StickerToSell;

import java.util.List;
import java.util.Optional;

public interface StickerToSellService {

    List<StickerToSellDTO> findAll();
    StickerToSell findById(String id);
    StickerToSellDTO create(StickerToSellCreationDTO creationDTO);
    StickerToSellDTO edit(String id, StickerToSellUpdateDTO updateDTO);
    void delete(String id);
    void deleteByStickerId(String stickerId);
    Optional<StickerToSell> findByStickerId(String stickerId);
}
