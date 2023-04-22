package br.com.ada.albuns.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.ada.albuns.model.dto.AlbumPrototipoDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.entity.AlbumPrototipo;
import br.com.ada.albuns.model.mapper.AlbumPrototipoMapper;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.repository.AlbumPrototipoRepository;
import br.com.ada.albuns.service.AlbumPrototipoService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AlbumPrototipoServiceImpl implements AlbumPrototipoService {

  private final AlbumPrototipoRepository repository;
  private final AlbumPrototipoMapper mapper;
  private final AlbumRepository albumRepository;

  public AlbumPrototipoServiceImpl(AlbumPrototipoRepository repository, AlbumPrototipoMapper mapper, AlbumRepository albumRepository) {
    this.repository = repository;
    this.mapper = mapper;
    this.albumRepository = albumRepository;
  }

  @Override
  public List<AlbumPrototipoDTO> findAll() {
    return mapper.parseListDTO(repository.findAll());
  }

  @Override
  public AlbumPrototipoDTO findById(String id) {
    Optional<AlbumPrototipo> albumPrototipoOptional = repository.findById(id);
    if (albumPrototipoOptional.isPresent()) {
      AlbumPrototipo albumPrototipo = albumPrototipoOptional.get();
      return mapper.parseDTO(albumPrototipo);
    }
    throw new EntityNotFoundException();
  }

  @Override
  public AlbumPrototipoDTO create(AlbumPrototipoDTO entity) {
    AlbumPrototipo albumPrototipo = mapper.parseEntity(entity);
    albumPrototipo.setId(null);

    albumPrototipo = repository.save(albumPrototipo);
    this.createPadraoAlbum(albumPrototipo);
    return mapper.parseDTO(albumPrototipo);
  }

  @Override
  public AlbumPrototipoDTO edit(String id, AlbumPrototipoDTO albumPrototipoDTO) {
    if (repository.existsById(id)) {
      AlbumPrototipo entity = mapper.parseEntity(albumPrototipoDTO);
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
  
  private void createPadraoAlbum(AlbumPrototipo albumPrototipo) {
	  Album album = Album.builder()
			  .id(null)
			  .usuarioId(null)
			  .albumPrototipoId(albumPrototipo.getId())
			  .build();
	  albumRepository.save(album);
  }

}
