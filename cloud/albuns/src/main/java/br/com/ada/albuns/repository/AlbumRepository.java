package br.com.ada.albuns.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ada.albuns.model.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, String>{
	Optional<Album> findByUserIdAndAlbumTemplateId(String userId, String albumTemplateId);
}
