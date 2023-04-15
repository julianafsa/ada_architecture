package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.Sticker;
import br.com.ada.stickers.model.entity.StickerTemplate;
import br.com.ada.stickers.model.mapper.StickerMapper;
import br.com.ada.stickers.model.mapper.StickerTemplateMapper;
import br.com.ada.stickers.repository.StickerRepository;
import br.com.ada.stickers.service.StickerService;
import br.com.ada.stickers.service.StickerTemplateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
        List<StickerDTO> stickersCreated = new ArrayList<>();
        boolean shouldRevertStickersCreation = false;

        try {
            List<StickerTemplateDTO> stickerTemplates = stickerTemplateService.findAll(Optional.ofNullable(createStickerMessage.getAlbumTemplateId()));
//            if (!stickerTemplatesResponse.getStatusCode().equals(HttpStatus.OK)) {
//                log.error("Error retrieving sticker templates: {}", stickerTemplatesResponse.getStatusCode());
//                throw new EntityNotFoundException("No sticker template found for this album template");
//            }

            //Album defaultAlbum = albumRepository.findByUserIdAndAlbumTemplateId(null, albumTemplateId).orElseThrow(() -> new EntityNotFoundException("Default album not found"));

            if (stickerTemplates != null) {
                for (StickerTemplateDTO stickerTemplate : stickerTemplates) {
                    List<StickerDTO> stickersCreatedInThisStep =this.createStickers(stickerTemplate, createStickerMessage);
                    if (stickersCreatedInThisStep != null) {
                        stickersCreated.addAll(stickersCreatedInThisStep);
                    } else {
                        shouldRevertStickersCreation = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            shouldRevertStickersCreation = true;
        }

        if (shouldRevertStickersCreation) {
            //this.revertStickersCreation(stickersCreated);
            return false;
        }

        return true;
    }


    private List<StickerDTO> createStickers(StickerTemplateDTO stickerTemplateDTO, CreateStickerMessage createStickerMessage) {
        List<StickerDTO> stickersCreated = new ArrayList<>();
        boolean shouldRevertStickersCreation = false;

        try {
            StickerCreationDTO stickerCreationDTO = StickerCreationDTO.builder()
                    .stickerTemplateId(stickerTemplateDTO.getId())
                    .albumId(createStickerMessage.getAlbumId())
                    .build();

            int quantity = this.calculateQuantityByRarity(stickerTemplateDTO);

            for (int i = 0; i < quantity; i++) {

                log.info("Creating sticker {} for {}", i + 1, stickerTemplateDTO.getDescription());
                StickerDTO response = this.create(stickerCreationDTO);

            }
        } catch (Exception e) {
            shouldRevertStickersCreation = true;
        }

        if (shouldRevertStickersCreation) {
            this.revertStickersCreation(stickersCreated);
            return null;
        }

        return stickersCreated;
    }

    private int calculateQuantityByRarity(StickerTemplateDTO stickerTemplateDTO) {
        return switch(stickerTemplateDTO.getRarity()) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 6;
            case 4 -> throw new RuntimeException("Fake exception");//10;
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
