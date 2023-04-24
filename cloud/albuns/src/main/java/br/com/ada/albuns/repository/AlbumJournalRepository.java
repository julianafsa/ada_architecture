package br.com.ada.albuns.repository;

import br.com.ada.albuns.model.entity.AlbumJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AlbumJournalRepository extends JpaRepository<AlbumJournal, Long> {
    @Query(value = "SELECT a.usuarioId FROM AlbumJournal a WHERE a.albumId = :albumId")
    Optional<String> findUsuarioIdByAlbumId(String albumId);
    Optional<AlbumJournal> findByAlbumId(String albumId);
}
