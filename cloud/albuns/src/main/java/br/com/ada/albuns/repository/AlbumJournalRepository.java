package br.com.ada.albuns.repository;

import br.com.ada.albuns.model.dto.AlbumDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ada.albuns.model.entity.AlbumJournal;

import java.util.List;
import java.util.Optional;

public interface AlbumJournalRepository extends JpaRepository<AlbumJournal, Long> {
    Optional<AlbumJournal> findByAlbumId(String albumId);
}
