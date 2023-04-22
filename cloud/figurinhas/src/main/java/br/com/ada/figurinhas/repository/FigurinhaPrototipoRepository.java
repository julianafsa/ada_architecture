package br.com.ada.stickers.repository;

import br.com.ada.stickers.model.entity.StickerTemplate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerTemplateRepository extends JpaRepository<StickerTemplate, String> {
	List<StickerTemplate> findByAlbumTemplateId(String albumTemplateId);
}
