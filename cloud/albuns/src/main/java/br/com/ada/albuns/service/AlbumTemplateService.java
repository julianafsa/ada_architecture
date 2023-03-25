package br.com.ada.albuns.service;

import java.util.List;

import br.com.ada.albuns.model.dto.AlbumTemplateDTO;

public interface AlbumTemplateService {

  List<AlbumTemplateDTO> findAll();
  AlbumTemplateDTO findByUuid(String uuid);
  AlbumTemplateDTO create(AlbumTemplateDTO entity);
  AlbumTemplateDTO edit(String uuid, AlbumTemplateDTO entity);
  void delete(String uuid);
  
}
