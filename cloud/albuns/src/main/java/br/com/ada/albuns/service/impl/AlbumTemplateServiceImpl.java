package br.com.ada.albuns.service.impl;

import java.util.List;
import java.util.Optional;

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
  public AlbumTemplateDTO findById(String id) {
    Optional<AlbumTemplate> albumTemplateOptional = repository.findById(id);
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

    repository.save(albumTemplate);
    return mapper.parseDTO(albumTemplate);
  }

  @Override
  public AlbumTemplateDTO edit(String id, AlbumTemplateDTO albumTemplateDTO) {
    if (repository.existsById(id)) {
      AlbumTemplate entity = mapper.parseEntity(albumTemplateDTO);
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

}
