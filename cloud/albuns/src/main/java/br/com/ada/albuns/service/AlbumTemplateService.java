package br.com.ada.albuns.service;

import java.util.List;

import br.com.ada.albuns.model.dto.AlbumTemplateDTO;

public interface AlbumTemplateService {

  List<AlbumTemplateDTO> findAll();

  AlbumTemplateDTO findById(Integer id);

  AlbumTemplateDTO create(AlbumTemplateDTO entity);
  
}
