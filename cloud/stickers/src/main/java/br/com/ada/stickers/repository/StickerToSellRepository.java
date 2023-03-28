package br.com.ada.stickers.repository;

import br.com.ada.stickers.model.entity.StickerToSell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StickerToSellRepository extends JpaRepository<StickerToSell, String> {
    Optional<StickerToSell> findByStickerId(String stickerId);
    void deleteByStickerId(String stickerId);
}
