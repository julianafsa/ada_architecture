package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.StickerBuyFromAlbumDTO;
import br.com.ada.stickers.model.dto.StickerBuyPackDTO;
import br.com.ada.stickers.model.entity.Sticker;

import java.util.List;

public interface StickerServiceWithJournal {
    List<Sticker> buyStickerPack(StickerBuyPackDTO stickerBuyPackDTO);
    Sticker buyStickerFromAlbum(StickerBuyFromAlbumDTO stickerBuyFromAlbumDTO);
}
