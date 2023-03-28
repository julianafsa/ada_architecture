package br.com.ada.stickers.strategy;

import br.com.ada.stickers.model.entity.Sticker;

import java.util.List;

public interface StickerPackStrategy {
    List<Sticker> createStickerPack(List<Sticker> stickers, Integer size);
}
