package br.com.ada.figurinhas.repository;

import br.com.ada.figurinhas.model.entity.FigurinhaPrototipo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FigurinhaPrototipoRepository extends JpaRepository<FigurinhaPrototipo, String> {
	List<FigurinhaPrototipo> findByAlbumPrototipoId(String albumPrototipoId);
}
