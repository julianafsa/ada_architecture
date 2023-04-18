package br.com.ada.albuns.service;

import br.com.ada.albuns.model.dto.AlbumDTO;

import java.util.List;
import java.util.Optional;

public interface AlbumService {

  List<AlbumDTO> findAll();
  AlbumDTO findById(String id);
  AlbumDTO create(AlbumDTO entity);
  AlbumDTO findDefaultAlbum(String albumTemplateId);
  Optional<String> findUserIdByAlbumId(String albumId);
}
