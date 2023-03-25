package br.com.ada.albuns.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.ada.albuns.model.dto.AlbumTemplateDTO;
import br.com.ada.albuns.model.entity.AlbumTemplate;
import br.com.ada.albuns.model.mapper.AlbumTemplateMapper;
import br.com.ada.albuns.repository.AlbumTemplateRepository;
import br.com.ada.albuns.service.AlbumTemplateService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AlbumTemplateServiceImpl implements AlbumTemplateService {

  private final AlbumTemplateRepository repository;
  private final AlbumTemplateMapper mapper;

  public AlbumTemplateServiceImpl(AlbumTemplateRepository repository, AlbumTemplateMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<AlbumTemplateDTO> findAll() {
    return mapper.parseListDTO(repository.findAll());
  }

  @Override
  public AlbumTemplateDTO findByUuid(String uuid) {
    Optional<AlbumTemplate> albumTemplateOptional = repository.findByUuid(uuid);
    if (albumTemplateOptional.isPresent()) {
      AlbumTemplate albumTemplate = albumTemplateOptional.get();
      return mapper.parseDTO(albumTemplate);
    }
    throw new EntityNotFoundException();
  }

  @Override
  public AlbumTemplateDTO create(AlbumTemplateDTO entity) {
    AlbumTemplate albumTemplate = mapper.parseEntity(entity);
    albumTemplate.setId(null);
    albumTemplate.setUuid(UUID.randomUUID().toString());
    repository.save(albumTemplate);
    return mapper.parseDTO(albumTemplate);
  }

  @Override
  public AlbumTemplateDTO edit(String uuid, AlbumTemplateDTO albumTemplateDTO) {
    AlbumTemplate dbEntity = repository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException());
    AlbumTemplate entity = mapper.parseEntity(albumTemplateDTO);
    entity.setId(dbEntity.getId());
    entity.setUuid(uuid);
    entity = repository.save(entity);
    return mapper.parseDTO(entity);
  }

  @Override
  public void delete(String uuid) {
    AlbumTemplate dbEntity = repository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException());
    repository.deleteById(dbEntity.getId());
  }

}
