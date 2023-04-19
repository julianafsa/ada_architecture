package br.com.ada.stickers.strategy;

import br.com.ada.stickers.model.entity.Figurinha;

import java.util.List;

public interface PacoteDeFigurinhaStrategy {
    List<Figurinha> createStickerPack(List<Figurinha> figurinhas, Integer size);
}
