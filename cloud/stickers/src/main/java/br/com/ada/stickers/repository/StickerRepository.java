package br.com.ada.stickers.repository;

import br.com.ada.stickers.model.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
}
