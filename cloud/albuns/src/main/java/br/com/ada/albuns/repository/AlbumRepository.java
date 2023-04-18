package br.com.ada.albuns.repository;

import br.com.ada.albuns.model.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, String>{
	Optional<Album> findByUserIdAndAlbumTemplateId(String userId, String albumTemplateId);
	@Query(value = "SELECT a.userId FROM Album a WHERE a.id = :albumId")
	Optional<String> findUserIdByAlbumId(String albumId);
}
