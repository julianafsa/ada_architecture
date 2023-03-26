package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.StickerTemplateCreationDTO;
import br.com.ada.stickers.model.dto.StickerTemplateDTO;
import br.com.ada.stickers.model.dto.StickerTemplateUpdateDTO;

import java.util.List;
import java.util.Optional;
public interface StickerTemplateService {
    List<StickerTemplateDTO> findAll(Optional<String> opAlbumTemplateId);
    StickerTemplateDTO findById(String id);
    StickerTemplateDTO create(StickerTemplateCreationDTO creationDTO);
    StickerTemplateDTO edit(String id, StickerTemplateUpdateDTO updateDTO);
    void delete(String id);
}
