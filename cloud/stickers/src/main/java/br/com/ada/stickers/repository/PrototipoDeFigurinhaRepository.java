package br.com.ada.stickers.repository;

import br.com.ada.stickers.model.entity.PrototipoDeFigurinha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrototipoDeFigurinhaRepository extends JpaRepository<PrototipoDeFigurinha, String> {
	List<PrototipoDeFigurinha> findByAlbumTemplateId(String albumTemplateId);
}
