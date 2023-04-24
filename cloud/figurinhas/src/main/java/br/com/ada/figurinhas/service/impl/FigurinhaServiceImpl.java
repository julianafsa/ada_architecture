package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.model.dto.*;
import br.com.ada.figurinhas.model.entity.Figurinha;
import br.com.ada.figurinhas.model.entity.FigurinhaPrototipo;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.model.mapper.FigurinhaPrototipoMapper;
import br.com.ada.figurinhas.repository.FigurinhaRepository;
import br.com.ada.figurinhas.service.FigurinhaService;
import br.com.ada.figurinhas.service.FigurinhaPrototipoService;
import br.com.ada.figurinhas.service.producer.FigurinhaErrorProducer;
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
    private final FigurinhaPrototipoService figurinhaPrototipoService;
    private final FigurinhaPrototipoMapper figurinhaPrototipoMapper;
    private final FigurinhaErrorProducer figurinhaErrorProducer;

    public FigurinhaServiceImpl(final FigurinhaRepository repository,
                              final FigurinhaMapper mapper,
                              final FigurinhaPrototipoService figurinhaPrototipoService,
                              final FigurinhaPrototipoMapper figurinhaPrototipoMapper,
                              final FigurinhaErrorProducer figurinhaErrorProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.figurinhaPrototipoService = figurinhaPrototipoService;
        this.figurinhaPrototipoMapper = figurinhaPrototipoMapper;
        this.figurinhaErrorProducer = figurinhaErrorProducer;
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
        final String errorMessage = "Figurinha with id " + id + " does not exist.";
        throw new EntityNotFoundException(errorMessage);
    }

    @Override
    //@Transactional
    public FigurinhaDTO create(final FigurinhaCreationDTO creationDTO) {
        Figurinha entity = mapper.parseEntity(creationDTO);
        FigurinhaPrototipo figurinhaPrototipoEntity = figurinhaPrototipoMapper.parseEntity(
                figurinhaPrototipoService.findById(creationDTO.getFigurinhaPrototipoId()));
        entity.setId(null);
        entity.setFigurinhaPrototipo(figurinhaPrototipoEntity);
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
    public boolean createFigurinhasForAlbum(CreateFigurinhaMessage createFigurinhaMessage) {
        try {
            List<FigurinhaPrototipoDTO> figurinhaPrototipos = figurinhaPrototipoService.findAll(Optional.ofNullable(createFigurinhaMessage.getAlbumPrototipoId()));
            List<Figurinha> figurinhasToCreateList = new ArrayList<>();
            if (!figurinhaPrototipos.isEmpty()) {
                figurinhaPrototipos.forEach(s ->
                        figurinhasToCreateList.addAll(this.createFigurinhas(s, createFigurinhaMessage))
                );
                // throw new RuntimeException("Error creating figurinhas");
                repository.saveAll(figurinhasToCreateList);
            }
        } catch (Exception e) {
            log.error("Error creating figurinhas...");
            log.error("Sending message error to Kafka...");
            figurinhaErrorProducer.send(createFigurinhaMessage.getAlbumId());
            return false;
        }
        return true;
    }

    private List<Figurinha> createFigurinhas(FigurinhaPrototipoDTO figurinhaPrototipoDTO, CreateFigurinhaMessage createFigurinhaMessage) {
        List<Figurinha> figurinhasToCreate = new ArrayList<>();
        try {
            FigurinhaCreationDTO figurinhaCreationDTO = FigurinhaCreationDTO.builder()
                    .figurinhaPrototipoId(figurinhaPrototipoDTO.getId())
                    .albumId(createFigurinhaMessage.getPadraoAlbumId())
                    .build();
            int quantity = this.calculateQuantityByRaridade(figurinhaPrototipoDTO);
            for (int i = 0; i < quantity; i++) {
                log.info("Creating figurinha {} for {}", i + 1, figurinhaPrototipoDTO.getDescription());
                //figurinhasToCreate.add(this.createAll(figurinhaCreationDTO));
                figurinhasToCreate.add(mapper.parseEntity(figurinhaCreationDTO));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calcualting figurinha quantities");
        }
        return figurinhasToCreate;
    }

    private int calculateQuantityByRaridade(FigurinhaPrototipoDTO figurinhaPrototipoDTO) {
        return switch(figurinhaPrototipoDTO.getRaridade()) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 6;
            case 4 -> 10; //throw new RuntimeException("Fake exception");
            default -> 0;
        };
    }

}
