package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.StickerJournalCreationDTO;
import br.com.ada.stickers.model.dto.StickerJournalDTO;

import java.util.List;
public interface StickerJournalService {
    List<StickerJournalDTO> findAll();
    StickerJournalDTO findById(String id);
    StickerJournalDTO create(StickerJournalCreationDTO creationDTO);
}
