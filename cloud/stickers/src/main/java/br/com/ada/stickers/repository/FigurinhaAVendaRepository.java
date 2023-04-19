package br.com.ada.stickers.repository;

import br.com.ada.stickers.model.entity.FigurinhaAVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FigurinhaAVendaRepository extends JpaRepository<FigurinhaAVenda, String> {
    Optional<FigurinhaAVenda> findByStickerId(String stickerId);
    void deleteByStickerId(String stickerId);
}
