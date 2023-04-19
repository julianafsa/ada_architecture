package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.model.dto.PrototipoDeAlbumDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.entity.PrototipoDeAlbum;
import br.com.ada.albuns.model.mapper.PrototipoDeAlbumMapper;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.repository.PrototipoDeAlbumRepository;
import br.com.ada.albuns.service.PrototipoDeAlbumService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrototipoDeAlbumServiceImpl implements PrototipoDeAlbumService {

  private final PrototipoDeAlbumRepository repository;
  private final PrototipoDeAlbumMapper mapper;
  private final AlbumRepository albumRepository;

  public PrototipoDeAlbumServiceImpl(PrototipoDeAlbumRepository repository, PrototipoDeAlbumMapper mapper, AlbumRepository albumRepository) {
    this.repository = repository;
    this.mapper = mapper;
    this.albumRepository = albumRepository;
  }

  @Override
  public List<PrototipoDeAlbumDTO> findAll() {
    return mapper.parseListDTO(repository.findAll());
  }

  @Override
  public PrototipoDeAlbumDTO findById(String id) {
    Optional<PrototipoDeAlbum> albumTemplateOptional = repository.findById(id);
    if (albumTemplateOptional.isPresent()) {
      PrototipoDeAlbum prototipoDeAlbum = albumTemplateOptional.get();
      return mapper.parseDTO(prototipoDeAlbum);
    }
    throw new EntityNotFoundException();
  }

  @Override
  public PrototipoDeAlbumDTO create(PrototipoDeAlbumDTO entity) {
    PrototipoDeAlbum prototipoDeAlbum = mapper.parseEntity(entity);
    prototipoDeAlbum.setId(null);

    prototipoDeAlbum = repository.save(prototipoDeAlbum);
    this.createDefaultAlbum(prototipoDeAlbum);
    return mapper.parseDTO(prototipoDeAlbum);
  }

  @Override
  public PrototipoDeAlbumDTO edit(String id, PrototipoDeAlbumDTO prototipoDeAlbumDTO) {
    if (repository.existsById(id)) {
      PrototipoDeAlbum entity = mapper.parseEntity(prototipoDeAlbumDTO);
      entity.setId(id);
      entity = repository.save(entity);
      return mapper.parseDTO(entity);
    }
    throw new EntityNotFoundException();
  }

  @Override
  public void delete(String id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException();
    }
    repository.deleteById(id);
  }
  
  private void createDefaultAlbum(PrototipoDeAlbum prototipoDeAlbum) {
	  Album album = Album.builder()
			  .id(null)
			  .userId(null)
			  .albumTemplateId(prototipoDeAlbum.getId())
			  .build();
	  albumRepository.save(album);
  }

}
