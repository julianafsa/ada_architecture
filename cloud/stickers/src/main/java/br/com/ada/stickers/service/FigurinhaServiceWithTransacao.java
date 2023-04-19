package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.FigurinhaBuyFromAlbumDTO;
import br.com.ada.stickers.model.dto.FigurinhaBuyPackDTO;
import br.com.ada.stickers.model.entity.Figurinha;

import java.util.List;

public interface FigurinhaServiceWithTransacao {
    List<Figurinha> buyStickerPack(FigurinhaBuyPackDTO figurinhaBuyPackDTO);
    Figurinha buyStickerFromAlbum(FigurinhaBuyFromAlbumDTO figurinhaBuyFromAlbumDTO);
}
