package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.Figurinha;
import br.com.ada.stickers.model.entity.PrototipoDeFigurinha;
import br.com.ada.stickers.model.mapper.FigurinhaMapper;
import br.com.ada.stickers.model.mapper.PrototipoDeFigurinhaMapper;
import br.com.ada.stickers.repository.FigurinhaRepository;
import br.com.ada.stickers.service.FigurinhaService;
import br.com.ada.stickers.service.PrototipoDeFigurinhaService;
import br.com.ada.stickers.service.producer.StickerErrorProducer;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FigurinhaServiceImpl implements FigurinhaService {

    private final FigurinhaRepository repository;
    private final FigurinhaMapper mapper;
    private final PrototipoDeFigurinhaService prototipoDeFigurinhaService;
    private final PrototipoDeFigurinhaMapper prototipoDeFigurinhaMapper;
    private final StickerErrorProducer stickerErrorProducer;

    public FigurinhaServiceImpl(final FigurinhaRepository repository,
                                final FigurinhaMapper mapper,
                                final PrototipoDeFigurinhaService prototipoDeFigurinhaService,
                                final PrototipoDeFigurinhaMapper prototipoDeFigurinhaMapper,
                                final StickerErrorProducer stickerErrorProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.prototipoDeFigurinhaService = prototipoDeFigurinhaService;
        this.prototipoDeFigurinhaMapper = prototipoDeFigurinhaMapper;
        this.stickerErrorProducer = stickerErrorProducer;
    }
    
    @Override
    public List<FigurinhaDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public FigurinhaDTO findById(final String id) {
        Optional<Figurinha> optional = repository.findById(id);
        if (optional.isPresent()) {
            final Figurinha entity = optional.get();
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    //@Transactional
    public FigurinhaDTO create(final FigurinhaCreationDTO creationDTO) {
        Figurinha entity = mapper.parseEntity(creationDTO);
        PrototipoDeFigurinha prototipoDeFigurinhaEntity = prototipoDeFigurinhaMapper.parseEntity(
                prototipoDeFigurinhaService.findById(creationDTO.getStickerTemplateId()));
        entity.setId(null);
        entity.setStickerTemplate(prototipoDeFigurinhaEntity);
        entity = repository.save(entity);
        //em.refresh(entity);
        return mapper.parseDTO(entity);
    }

    @Override
    public Figurinha edit(final String id, final FigurinhaUpdateDTO updateDTO) {
        final Optional<Figurinha> optional = repository.findById(id);
        if (optional.isPresent()) {
            Figurinha entity = optional.get();
            entity.setId(id);
            entity = repository.save(entity);
            return entity;
        }
        throw new EntityNotFoundException();
    }

    @Override
    public List<Figurinha> editAll(final List<Figurinha> entities) {
        for (Figurinha figurinhaToUpdate : entities) {
            if (!repository.existsById(figurinhaToUpdate.getId())) {
                throw new EntityNotFoundException();
            }
        }
        repository.saveAll(entities);
        return entities;
    }

    @Override
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(id);
    }

    @Override
    public List<Figurinha> findByAlbumId(String albumId) {
        return repository.findByAlbumId(albumId);
    }


    @Override
    public boolean createStickersForAlbum(CreateFigurinhaMessage createFigurinhaMessage) {
        try {
            List<PrototipoDeFigurinhaDTO> stickerTemplates = prototipoDeFigurinhaService.findAll(Optional.ofNullable(createFigurinhaMessage.getAlbumTemplateId()));
            List<Figurinha> stickersToCreateList = new ArrayList<>();
            if (!stickerTemplates.isEmpty()) {
                stickerTemplates.forEach(s ->
                        stickersToCreateList.addAll(this.createStickers(s, createFigurinhaMessage))
                );
                // throw new RuntimeException("Error creating stickers");
                repository.saveAll(stickersToCreateList);
            }
        } catch (Exception e) {
            log.error("Error creating stickers...");
            log.error("Sending message error to Kafka...");
            stickerErrorProducer.send(createFigurinhaMessage.getAlbumId());
            return false;
        }
        return true;
    }

    private List<Figurinha> createStickers(PrototipoDeFigurinhaDTO prototipoDeFigurinhaDTO, CreateFigurinhaMessage createFigurinhaMessage) {
        List<Figurinha> stickersToCreate = new ArrayList<>();
        try {
            FigurinhaCreationDTO figurinhaCreationDTO = FigurinhaCreationDTO.builder()
                    .stickerTemplateId(prototipoDeFigurinhaDTO.getId())
                    .albumId(createFigurinhaMessage.getDefaultAlbumId())
                    .build();
            int quantity = this.calculateQuantityByRarity(prototipoDeFigurinhaDTO);
            for (int i = 0; i < quantity; i++) {
                log.info("Creating sticker {} for {}", i + 1, prototipoDeFigurinhaDTO.getDescription());
                //stickersToCreate.add(this.createAll(stickerCreationDTO));
                stickersToCreate.add(mapper.parseEntity(figurinhaCreationDTO));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calcualting sticker quantities");
        }
        return stickersToCreate;
    }

    private int calculateQuantityByRarity(PrototipoDeFigurinhaDTO prototipoDeFigurinhaDTO) {
        return switch(prototipoDeFigurinhaDTO.getRarity()) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 6;
            case 4 -> 10; //throw new RuntimeException("Fake exception");
            default -> 0;
        };
    }

}
