package br.com.ada.figurinhas.repository;

import br.com.ada.figurinhas.model.entity.FigurinhaToSell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FigurinhaToSellRepository extends JpaRepository<FigurinhaToSell, String> {
    Optional<FigurinhaToSell> findByFigurinhaId(String figurinhaId);
    void deleteByFigurinhaId(String figurinhaId);
}
