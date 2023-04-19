package br.com.ada.stickers.repository;

import br.com.ada.stickers.model.entity.TransacaoFigurinha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoFigurinhaRepository extends JpaRepository<TransacaoFigurinha, String> {
}
