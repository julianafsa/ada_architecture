package br.com.ada.stickers.repository;

import br.com.ada.stickers.model.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StickerRepository extends JpaRepository<Sticker, String> {
    List<Sticker> findByAlbumId(String albumId);
}
