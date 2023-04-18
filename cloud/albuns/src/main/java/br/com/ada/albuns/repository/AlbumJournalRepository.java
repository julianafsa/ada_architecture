package br.com.ada.albuns.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ada.albuns.model.entity.AlbumJournal;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AlbumJournalRepository extends JpaRepository<AlbumJournal, Long> {
    @Query(value = "SELECT a.userId FROM AlbumJournal a WHERE a.albumId = :albumId")
    Optional<String> findUserIdByAlbumId(String albumId);
}
