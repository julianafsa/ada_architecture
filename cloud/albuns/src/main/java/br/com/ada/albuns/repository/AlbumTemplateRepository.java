package br.com.ada.albuns.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ada.albuns.model.entity.AlbumTemplate;

public interface AlbumTemplateRepository extends JpaRepository<AlbumTemplate, Long>{
	Optional<AlbumTemplate> findByUuid(String uuid);
  
}
