package br.com.ada.stickers.repository;

import br.com.ada.stickers.model.entity.StickerToSell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerToSellRepository extends JpaRepository<StickerToSell, String> {
}
