package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.mapper.AlbumMapper;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.service.AlbumService;
import br.com.ada.albuns.service.StickerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  AlbumServiceImpl implements AlbumService {

  private final AlbumRepository repository;
  private final AlbumMapper mapper;
  private final StickerService stickerService;

  public AlbumServiceImpl(AlbumRepository repository, AlbumMapper mapper,
		  StickerService stickerService) {
    this.repository = repository;
    this.mapper = mapper;
    this.stickerService = stickerService;
  }

  @Override
  public List<AlbumDTO> findAll() {
    return mapper.parseListDTO(repository.findAll());
  }

  @Override
  public AlbumDTO findById(String id) {
    Album album = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    return mapper.parseDTO(album);
  }

  @Override
  public AlbumDTO create(AlbumDTO entity) {
    Album album = mapper.parseEntity(entity);
    album.setId(null);

    album = repository.save(album);
    
    stickerService.createStickersForAlbum(entity.getAlbumTemplateId());
    
    return mapper.parseDTO(album);
  }

  @Override
  public AlbumDTO findDefaultAlbum(String albumTemplateId) {
    Album defaultAlbum = repository.findByUserIdAndAlbumTemplateId(null, albumTemplateId).orElseThrow(() -> new EntityNotFoundException());
    return mapper.parseDTO(defaultAlbum);
  }
}
