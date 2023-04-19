package br.com.ada.albuns.repository;

import br.com.ada.albuns.model.entity.TransacaoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransacaoAlbumRepository extends JpaRepository<TransacaoAlbum, Long> {
    @Query(value = "SELECT a.userId FROM TransacaoAlbum a WHERE a.albumId = :albumId")
    Optional<String> findUserIdByAlbumId(String albumId);
    Optional<TransacaoAlbum> findByAlbumId(String albumId);
}
