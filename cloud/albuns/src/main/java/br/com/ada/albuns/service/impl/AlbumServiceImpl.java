package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.dto.CreateStickerMessage;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.mapper.AlbumMapper;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.service.AlbumService;
import br.com.ada.albuns.service.producer.AlbumProducer;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class  AlbumServiceImpl implements AlbumService {

    private final AlbumRepository repository;
    private final AlbumMapper mapper;
    private final AlbumProducer producer;

    public AlbumServiceImpl(AlbumRepository repository, AlbumMapper mapper, AlbumProducer producer) {
        this.repository = repository;
        this.mapper = mapper;
        this.producer = producer;
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

        try{
            album = repository.save(album);
            final String albumTemplateId = album.getAlbumTemplateId();
            final Album defaultAlbum = repository.findByUserIdAndAlbumTemplateId(null, albumTemplateId).orElseThrow(() -> new EntityNotFoundException());
            CreateStickerMessage message = CreateStickerMessage.builder()
                    .albumId(album.getId())
                    .albumTemplateId(albumTemplateId)
                    .defaultAlbumId(defaultAlbum.getId())
                    .build();

            producer.send(message);

        } catch (RuntimeException e){
            throw new RuntimeException("Error creating album template...");
        }
        return mapper.parseDTO(album);
    }

    @Override
    public AlbumDTO findDefaultAlbum(String albumTemplateId) {
        Album defaultAlbum = repository.findByUserIdAndAlbumTemplateId(null, albumTemplateId).orElseThrow(() -> new EntityNotFoundException());
        return mapper.parseDTO(defaultAlbum);
    }

    @Override
    public void delete(String albumId) {
        Optional<Album> album = repository.findById(albumId);
        if(album.isPresent()){
            log.info("Deleting album id: " + album.get().getId());
            repository.delete(album.get());
        }else{
            throw new RuntimeException("Error to find album");
        }
    }

    @Override
    public Optional<Album> findByAlbumId(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<String> findUserIdByAlbumId(String albumId) {
        return repository.findUserIdByAlbumId(albumId);
    }

}
