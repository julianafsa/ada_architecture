package br.com.ada.albuns.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ada.albuns.model.entity.AlbumJournal;

public interface AlbumJournalRepository extends JpaRepository<AlbumJournal, Long> {
}
