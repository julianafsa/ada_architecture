package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.model.dto.*;
import br.com.ada.figurinhas.model.entity.FigurinhaToSell;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapperImpl;
import br.com.ada.figurinhas.model.mapper.FigurinhaToSellMapper;
import br.com.ada.figurinhas.model.mapper.FigurinhaToSellMapperImpl;
import br.com.ada.figurinhas.repository.FigurinhaToSellRepository;
import br.com.ada.figurinhas.service.FigurinhaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FigurinhaToSellServiceImplTest {

    private FigurinhaToSellRepository repository;
    private FigurinhaToSellMapper mapper;
    private FigurinhaToSellServiceImpl service;
    private FigurinhaService figurinhaService;
    private FigurinhaMapper figurinhaMapper;

    @BeforeEach
    public void init() {
        this.repository = mock(FigurinhaToSellRepository.class);
        this.mapper = new FigurinhaToSellMapperImpl();
        this.figurinhaService = mock(FigurinhaService.class);
        this.figurinhaMapper = new FigurinhaMapperImpl();
        this.service = new FigurinhaToSellServiceImpl(
                repository, mapper, figurinhaService, figurinhaMapper);
    }

    @Test
    void testFindAll() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaToSellDTO figurinhaToSellDTO = buildFigurinhaToSellDTO(id);
        Mockito.when(repository.findAll()).
                thenReturn((List.of(mapper.parseEntity(figurinhaToSellDTO))));
        final List<FigurinhaToSellDTO> response = service.findAll();
        assertEquals(1, response.size());
        assertEquals(id, response.get(0).getId());
        assertEquals("1", response.get(0).getFigurinha().getAlbumId());
        assertEquals(1, response.get(0).getFigurinha().getFigurinhaPrototipo().getNumber());
    }

    @Test
    void testeFindById() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaToSell figurinhaToSell = mapper.parseEntity(buildFigurinhaToSellDTO(id));
        Mockito.when(repository.findById(id)).
                thenReturn((Optional.of(figurinhaToSell)));
        final FigurinhaToSell response = service.findById(id);
        assertEquals(id, response.getId());
        assertEquals("1", response.getFigurinha().getAlbumId());
        assertEquals(1, response.getFigurinha().getFigurinhaPrototipo().getNumber());
    }

    @Test
    void testeFindByIdNotFound() {
        final String id = UUID.randomUUID().toString();
        Mockito.when(repository.findById(id)).
                thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    void testCreate() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaToSellCreationDTO figurinhaCreationDTO = buildFigurinhaToSellCreationDTO();
        final FigurinhaToSellDTO figurinhaToSellDTO = buildFigurinhaToSellDTO(id);
        Mockito.when(repository.save(any()))
                .thenReturn(mapper.parseEntity(figurinhaToSellDTO));
        final FigurinhaToSellDTO response = service.create(figurinhaCreationDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getFigurinha().getAlbumId());
        assertEquals(1, response.getFigurinha().getFigurinhaPrototipo().getNumber());
    }

    @Test
    void testEdit() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaToSellDTO figurinhaToSellDTO = buildFigurinhaToSellDTO(id);
        final FigurinhaToSell figurinhaToSell = mapper.parseEntity(figurinhaToSellDTO);
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(figurinhaToSell));
        Mockito.when(repository.save(figurinhaToSell))
                .thenReturn(figurinhaToSell);
        final FigurinhaToSellDTO response = service.edit(id, buildFigurinhaToSellUpdateDTO());
        assertEquals(id, response.getId());
        assertEquals("1", response.getFigurinha().getAlbumId());
        assertEquals(1, response.getFigurinha().getFigurinhaPrototipo().getNumber());
    }

    @Test
    void testEditIdNotFound() {
        final String id = UUID.randomUUID().toString();
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
                service.edit(id, buildFigurinhaToSellUpdateDTO()));
    }

    @Test
    void testDelete() {
        final String id = UUID.randomUUID().toString();
        Mockito.when(repository.existsById(id))
                .thenReturn(Boolean.TRUE);
        service.delete(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteIdNotFound() {
        final String id = UUID.randomUUID().toString();
        Mockito.when(repository.existsById(id))
                .thenReturn(Boolean.FALSE);
        assertThrows(EntityNotFoundException.class, () -> service.delete(id));
    }

    @Test
    void deleteByFigurinhaId() {
        final String figurinhaId = UUID.randomUUID().toString();
        Mockito.when(repository.findByFigurinhaId(figurinhaId))
                .thenReturn(Optional.of(mapper.parseEntity(buildFigurinhaToSellDTO(figurinhaId))));
        service.deleteByFigurinhaId(figurinhaId);
        verify(repository, times(1)).deleteById(figurinhaId);
    }

    @Test
    void deleteByFigurinhaIdNotFound() {
        final String figurinhaId = UUID.randomUUID().toString();
        Mockito.when(repository.findByFigurinhaId(figurinhaId))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.deleteByFigurinhaId(figurinhaId));
    }

    @Test
    void findByFigurinhaId() {
        final String figurinhaId = UUID.randomUUID().toString();
        Mockito.when(repository.findByFigurinhaId(figurinhaId)).
                thenReturn(Optional.of(mapper.parseEntity(buildFigurinhaToSellDTO(figurinhaId))));
        final Optional<FigurinhaToSell> response = service.findByFigurinhaId(figurinhaId);
        assertEquals(figurinhaId, response.get().getId());
        assertEquals("1", response.get().getFigurinha().getAlbumId());
        assertEquals(1, response.get().getFigurinha().getFigurinhaPrototipo().getNumber());
    }

    private FigurinhaToSellDTO buildFigurinhaToSellDTO(String id) {
        final FigurinhaToSellDTO figurinhaToSellDTO = FigurinhaToSellDTO.builder()
                .id(id)
                .figurinha(buildFigurinhaDTO())
                .price(new BigDecimal(1))
                .build();
        return figurinhaToSellDTO;
    }

    private FigurinhaDTO buildFigurinhaDTO() {
        final FigurinhaDTO figurinhaDTO = FigurinhaDTO.builder()
                .id(UUID.randomUUID().toString())
                .figurinhaPrototipo(buildFigurinhaPrototipoDTO())
                .albumId("1")
                .build();
        return figurinhaDTO;
    }

    private FigurinhaPrototipoDTO buildFigurinhaPrototipoDTO() {
        final FigurinhaPrototipoDTO figurinhaPrototipoDTO = FigurinhaPrototipoDTO.builder()
                .id(UUID.randomUUID().toString())
                .albumPrototipoId(UUID.randomUUID().toString())
                .number(1)
                .description("Figurinha 1")
                .image("base64image")
                .raridade(1)
                .figurinhaPrice(new BigDecimal(1))
                .build();
        return figurinhaPrototipoDTO;
    }

    private FigurinhaToSellCreationDTO buildFigurinhaToSellCreationDTO() {
        final FigurinhaMapper figurinhaMapper = new FigurinhaMapperImpl();
        final FigurinhaToSellCreationDTO figurinhaToSellCreationDTO = FigurinhaToSellCreationDTO.builder()
                .figurinha(figurinhaMapper.parseEntity(buildFigurinhaDTO()))
                .price(new BigDecimal(1))
                .build();
        return figurinhaToSellCreationDTO;
    }

    private FigurinhaToSellUpdateDTO buildFigurinhaToSellUpdateDTO() {
        final FigurinhaToSellUpdateDTO figurinhaToSellUpdateDTO = FigurinhaToSellUpdateDTO.builder()
                .price(new BigDecimal(1))
                .build();
        return figurinhaToSellUpdateDTO;
    }

}