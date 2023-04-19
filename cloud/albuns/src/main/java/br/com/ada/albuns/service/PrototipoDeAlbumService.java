package br.com.ada.albuns.service;

import br.com.ada.albuns.model.dto.PrototipoDeAlbumDTO;

import java.util.List;

public interface PrototipoDeAlbumService {

  List<PrototipoDeAlbumDTO> findAll();
  PrototipoDeAlbumDTO findById(String id);
  PrototipoDeAlbumDTO create(PrototipoDeAlbumDTO entity);
  PrototipoDeAlbumDTO edit(String id, PrototipoDeAlbumDTO entity);
  void delete(String id);
  
}
