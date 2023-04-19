package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.model.dto.TransacaoAlbumDTO;
import br.com.ada.albuns.model.mapper.TransacaoAlbumMapper;
import br.com.ada.albuns.repository.TransacaoAlbumRepository;
import br.com.ada.albuns.service.TransacaoAlbumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoAlbumServiceImpl implements TransacaoAlbumService {

  private final TransacaoAlbumRepository repository;
  private final TransacaoAlbumMapper mapper;

  public TransacaoAlbumServiceImpl(TransacaoAlbumRepository repository, TransacaoAlbumMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<TransacaoAlbumDTO> findAll() {
    return mapper.parseListDTO(repository.findAll());
  }
}
