package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.StickerCreationDTO;
import br.com.ada.stickers.model.dto.StickerDTO;
import br.com.ada.stickers.model.dto.StickerUpdateDTO;
import br.com.ada.stickers.model.entity.Sticker;

import java.util.List;

public interface StickerService {

    List<StickerDTO> findAll();
    StickerDTO findById(String id);
    StickerDTO create(StickerCreationDTO creationDTO);
    Sticker edit(String id, StickerUpdateDTO updateDTO);
    void delete(String id);
    List<Sticker> findByAlbumId(String albumId);
    List<Sticker> editAll(List<Sticker> entities);
}
