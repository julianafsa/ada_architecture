package br.com.ada.albuns.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.ada.albuns.model.dto.AlbumJournalDTO;
import br.com.ada.albuns.model.mapper.AlbumJournalMapper;
import br.com.ada.albuns.repository.AlbumJournalRepository;
import br.com.ada.albuns.service.AlbumJournalService;

@Service
public class AlbumJournalServiceImpl implements AlbumJournalService {

  private final AlbumJournalRepository repository;
  private final AlbumJournalMapper mapper;

  public AlbumJournalServiceImpl(AlbumJournalRepository repository, AlbumJournalMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<AlbumJournalDTO> findAll() {
    return mapper.parseListDTO(repository.findAll());
  }
}
