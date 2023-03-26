package br.com.ada.albuns.service;

import java.util.List;

import br.com.ada.albuns.model.dto.AlbumDTO;

public interface AlbumService {

  List<AlbumDTO> findAll();
  AlbumDTO findById(String id);
  AlbumDTO create(AlbumDTO entity);
  AlbumDTO findDefaultAlbum(String albumTemplateId);
}
