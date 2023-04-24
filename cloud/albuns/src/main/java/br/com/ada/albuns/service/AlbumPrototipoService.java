package br.com.ada.albuns.service;

import java.util.List;

import br.com.ada.albuns.model.dto.AlbumPrototipoDTO;

public interface AlbumPrototipoService {

  List<AlbumPrototipoDTO> findAll();
  AlbumPrototipoDTO findById(String id);
  AlbumPrototipoDTO create(AlbumPrototipoDTO entity);
  AlbumPrototipoDTO edit(String id, AlbumPrototipoDTO entity);
  void delete(String id);
  
}
