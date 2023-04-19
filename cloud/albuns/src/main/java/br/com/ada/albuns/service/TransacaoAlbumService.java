package br.com.ada.albuns.service;

import br.com.ada.albuns.model.dto.TransacaoAlbumDTO;

import java.util.List;

public interface TransacaoAlbumService {

  List<TransacaoAlbumDTO> findAll();
}
