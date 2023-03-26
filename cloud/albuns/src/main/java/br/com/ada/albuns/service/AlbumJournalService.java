package br.com.ada.albuns.service;

import java.util.List;

import br.com.ada.albuns.model.dto.AlbumJournalDTO;

public interface AlbumJournalService {

  List<AlbumJournalDTO> findAll();
}
