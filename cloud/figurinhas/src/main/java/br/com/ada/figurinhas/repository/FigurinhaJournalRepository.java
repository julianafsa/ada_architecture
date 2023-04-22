package br.com.ada.stickers.repository;

import br.com.ada.stickers.model.entity.StickerJournal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerJournalRepository extends JpaRepository<StickerJournal, String> {
}
