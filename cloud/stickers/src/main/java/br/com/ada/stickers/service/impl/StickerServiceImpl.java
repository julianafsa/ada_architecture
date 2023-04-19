package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.Sticker;
import br.com.ada.stickers.model.entity.StickerTemplate;
import br.com.ada.stickers.model.mapper.StickerMapper;
import br.com.ada.stickers.model.mapper.StickerTemplateMapper;
import br.com.ada.stickers.repository.StickerRepository;
import br.com.ada.stickers.service.StickerService;
import br.com.ada.stickers.service.StickerTemplateService;
import br.com.ada.stickers.service.producer.StickerErrorProducer;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StickerServiceImpl implements StickerService {

    private final StickerRepository repository;
    private final StickerMapper mapper;
    private final StickerTemplateService stickerTemplateService;
    private final StickerTemplateMapper stickerTemplateMapper;

    @Autowired
    private StickerErrorProducer stickerErrorProducer;

    public StickerServiceImpl(final StickerRepository repository,
                              final StickerMapper mapper,
                              final StickerTemplateService stickerTemplateService,
                              final StickerTemplateMapper stickerTemplateMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.stickerTemplateService = stickerTemplateService;
        this.stickerTemplateMapper = stickerTemplateMapper;
    }
    
    @Override
    public List<StickerDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public StickerDTO findById(final String id) {
        Optional<Sticker> optional = repository.findById(id);
        if (optional.isPresent()) {
            final Sticker entity = optional.get();
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    //@Transactional
    public StickerDTO create(final StickerCreationDTO creationDTO) {
        Sticker entity = mapper.parseEntity(creationDTO);
        StickerTemplate stickerTemplateEntity = stickerTemplateMapper.parseEntity(
                stickerTemplateService.findById(creationDTO.getStickerTemplateId()));
        entity.setId(null);
        entity.setStickerTemplate(stickerTemplateEntity);
        entity = repository.save(entity);
        //em.refresh(entity);
        return mapper.parseDTO(entity);
    }

    @Override
    //@Transactional
    public Sticker createAll(final StickerCreationDTO creationDTO) {
        Sticker entity = mapper.parseEntity(creationDTO);
        StickerTemplate stickerTemplateEntity = stickerTemplateMapper.parseEntity(
                stickerTemplateService.findById(creationDTO.getStickerTemplateId()));
        entity.setId(null);
        entity.setStickerTemplate(stickerTemplateEntity);
        //em.refresh(entity);
        return entity;
    }

    @Override
    public Sticker edit(final String id, final StickerUpdateDTO updateDTO) {
        final Optional<Sticker> optional = repository.findById(id);
        if (optional.isPresent()) {
            Sticker entity = optional.get();
            entity.setId(id);
            entity = repository.save(entity);
            return entity;
        }
        throw new EntityNotFoundException();
    }

    @Override
    public List<Sticker> editAll(final List<Sticker> entities) {
        for (Sticker stickerToUpdate : entities) {
            if (!repository.existsById(stickerToUpdate.getId())) {
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
    public List<Sticker> findByAlbumId(String albumId) {
        return repository.findByAlbumId(albumId);
    }


    @Override
    public boolean createStickersForAlbum(CreateStickerMessage createStickerMessage) {
        try {
            List<StickerTemplateDTO> stickerTemplates = stickerTemplateService.findAll(Optional.ofNullable(createStickerMessage.getAlbumTemplateId()));
            List<Sticker> stickersToCreateList = new ArrayList<>();
            if (!stickerTemplates.isEmpty()){
                stickerTemplates.stream().forEach(s ->
                        stickersToCreateList.addAll(this.createStickers(s, createStickerMessage))
                );
                //throw new RuntimeException("teste");
                repository.saveAll(stickersToCreateList);
            }

        } catch (Exception e) {
            log.error("Sending message error to Kafka");
            stickerErrorProducer.send(createStickerMessage.getAlbumId());
            return false;
        }

        return true;
    }


    private List<Sticker> createStickers(StickerTemplateDTO stickerTemplateDTO, CreateStickerMessage createStickerMessage) {
        List<Sticker> stickersToCreate = new ArrayList<>();
        try {
            StickerCreationDTO stickerCreationDTO = StickerCreationDTO.builder()
                    .stickerTemplateId(stickerTemplateDTO.getId())
                    .albumId(createStickerMessage.getDefaultAlbumId())
                    .build();
            int quantity = this.calculateQuantityByRarity(stickerTemplateDTO);
            for (int i = 0; i < quantity; i++) {

                log.info("Creating sticker {} for {}", i + 1, stickerTemplateDTO.getDescription());
                stickersToCreate.add(this.createAll(stickerCreationDTO));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calcualting sticker quantities");
        }
        return stickersToCreate;
    }

    private int calculateQuantityByRarity(StickerTemplateDTO stickerTemplateDTO) {
        return switch(stickerTemplateDTO.getRarity()) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 6;
            case 4 -> 10; //throw new RuntimeException("Fake exception");
            default -> 0;
        };
    }

    private void revertStickersCreation(List<StickerDTO> stickersToRevert) {
        stickersToRevert.forEach(stickerToRevert -> {
            try {
                log.info("Reverting sticker {}", stickerToRevert.getId());
               // stickerClient.delete(stickerToRevert.getId());
            } catch(Exception e) {
                log.error("Error reverting sticker creation for sticker {}: {}", stickerToRevert.getId(), e.getMessage());
            }
        });
    }
}
