package br.com.ada.albuns.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ada.albuns.service.producer.AlbumProducer;
import org.springframework.stereotype.Service;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.mapper.AlbumMapper;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.service.AlbumService;
import br.com.ada.albuns.service.StickerService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository repository;
    private final AlbumMapper mapper;
    private final StickerService stickerService;
    private final AlbumProducer producer;

    public AlbumServiceImpl(AlbumRepository repository, AlbumMapper mapper,
                            StickerService stickerService, AlbumProducer producer) {
        this.repository = repository;
        this.mapper = mapper;
        this.stickerService = stickerService;
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

        boolean shouldRevertAlbumCreation = true;
        album = repository.save(album);

        try {
//            if (!stickerService.createStickersForAlbum(entity.getAlbumTemplateId())) {
//                shouldRevertAlbumCreation = true;
//            }

            Map<String, Object> map = new HashMap<>();

            map.put("albumId", album.getId());
            map.put("albumTemplateId", album.getAlbumTemplateId());
            producer.send(map);
            shouldRevertAlbumCreation = true;


        } catch (Exception e) {
            shouldRevertAlbumCreation = true;
        }

//        if (shouldRevertAlbumCreation) {
//            repository.delete(album);
//            throw new RuntimeException("Error creating album");
//        }

        return mapper.parseDTO(album);
    }

    @Override
    public AlbumDTO findDefaultAlbum(String albumTemplateId) {
        Album defaultAlbum = repository.findByUserIdAndAlbumTemplateId(null, albumTemplateId).orElseThrow(() -> new EntityNotFoundException());
        return mapper.parseDTO(defaultAlbum);
    }
}
