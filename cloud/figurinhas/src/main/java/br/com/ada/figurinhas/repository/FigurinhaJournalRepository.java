package br.com.ada.figurinhas.repository;

import br.com.ada.figurinhas.model.entity.FigurinhaJournal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FigurinhaJournalRepository extends JpaRepository<FigurinhaJournal, String> {
}
