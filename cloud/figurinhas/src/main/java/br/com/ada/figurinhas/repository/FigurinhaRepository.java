package br.com.ada.figurinhas.repository;

import br.com.ada.figurinhas.model.entity.Figurinha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FigurinhaRepository extends JpaRepository<Figurinha, String> {
    List<Figurinha> findByAlbumId(String albumId);
}
