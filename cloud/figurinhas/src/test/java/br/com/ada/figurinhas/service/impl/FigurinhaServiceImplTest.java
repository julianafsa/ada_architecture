package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.model.dto.*;
import br.com.ada.figurinhas.model.entity.Figurinha;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapperImpl;
import br.com.ada.figurinhas.model.mapper.FigurinhaPrototipoMapper;
import br.com.ada.figurinhas.model.mapper.FigurinhaPrototipoMapperImpl;
import br.com.ada.figurinhas.repository.FigurinhaRepository;
import br.com.ada.figurinhas.service.FigurinhaService;
import br.com.ada.figurinhas.service.FigurinhaPrototipoService;
import br.com.ada.figurinhas.service.producer.FigurinhaErrorProducer;
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
import static org.mockito.Mockito.*;

class FigurinhaServiceImplTest {

    private FigurinhaRepository repository;
    private FigurinhaMapper mapper;
    private FigurinhaService service;
    private FigurinhaPrototipoService figurinhaPrototipoService;
    private FigurinhaPrototipoMapper figurinhaPrototipoMapper;
    private FigurinhaErrorProducer figurinhaErrorProducer;

    @BeforeEach
    public void init() {
        this.repository = mock(FigurinhaRepository.class);
        this.mapper = new FigurinhaMapperImpl();
        this.figurinhaPrototipoService = mock(FigurinhaPrototipoService.class);
        this.figurinhaPrototipoMapper = new FigurinhaPrototipoMapperImpl();
        this.figurinhaErrorProducer = mock(FigurinhaErrorProducer.class);
        this.service = new FigurinhaServiceImpl(
                repository, mapper, figurinhaPrototipoService, figurinhaPrototipoMapper, figurinhaErrorProducer);
    }

    @Test
    void testFindAll() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaDTO figurinhaDTO = buildFigurinhaDTO(id);
        Mockito.when(repository.findAll()).
                thenReturn((List.of(mapper.parseEntity(figurinhaDTO))));
        final List<FigurinhaDTO> response = service.findAll();
        assertEquals(1, response.size());
        assertEquals(id, response.get(0).getId());
        assertEquals("1", response.get(0).getAlbumId());
        assertEquals(1, response.get(0).getFigurinhaPrototipo().getNumber());
    }

    @Test
    void testFindById() {
        final String id = UUID.randomUUID().toString();
        final Figurinha figurinha = mapper.parseEntity(buildFigurinhaDTO(id));
        Mockito.when(repository.findById(id)).
                thenReturn((Optional.of(figurinha)));
        final FigurinhaDTO response = service.findById(id);
        assertEquals(id, response.getId());
        assertEquals("1", response.getAlbumId());
        assertEquals(1, response.getFigurinhaPrototipo().getNumber());
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
        final FigurinhaCreationDTO figurinhaCreationDTO = buildFigurinhaCreationDTO(id);
        final Figurinha figurinha = mapper.parseEntity(buildFigurinhaDTO(id));
        Mockito.when(repository.save(any()))
                .thenReturn(figurinha);
        final FigurinhaDTO response = service.create(figurinhaCreationDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getAlbumId());
        assertEquals(1, response.getFigurinhaPrototipo().getNumber());
    }

    @Test
    void testEdit() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaDTO figurinhaDTO = buildFigurinhaDTO(id);
        final Figurinha figurinha = mapper.parseEntity(figurinhaDTO);
        final FigurinhaUpdateDTO figurinhaUpdateDTO = buildFigurinhaToSellDTO(id);
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(figurinha));
        Mockito.when(repository.save(figurinha))
                .thenReturn(figurinha);
        final Figurinha response = service.edit(id, figurinhaUpdateDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getAlbumId());
        assertEquals(1, response.getFigurinhaPrototipo().getNumber());
    }

    @Test
    void testEditIdNotFound() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaUpdateDTO figurinhaUpdateDTO = buildFigurinhaToSellDTO(id);
        Mockito.when(repository.existsById(id))
                .thenReturn(Boolean.FALSE);
        assertThrows(EntityNotFoundException.class, () ->
                service.edit(id, figurinhaUpdateDTO));
    }

    @Test
    void testEditAll() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaDTO figurinhaDTO = buildFigurinhaDTO(id);
        final Figurinha figurinha = mapper.parseEntity(figurinhaDTO);
        final List<Figurinha> list = List.of(figurinha);
        Mockito.when(repository.existsById(id))
                .thenReturn(Boolean.TRUE);
        Mockito.when(repository.saveAll(list))
                .thenReturn(list);
        final List<Figurinha> response = service.editAll(list);
        assertEquals(1, response.size());
        assertEquals("1", response.get(0).getAlbumId());
        assertEquals(1, response.get(0).getFigurinhaPrototipo().getNumber());
    }

    @Test
    void testEditAllNotFoundException() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaDTO figurinhaDTO = buildFigurinhaDTO(id);
        final Figurinha figurinha = mapper.parseEntity(figurinhaDTO);
        final List<Figurinha> list = List.of(figurinha);
        Mockito.when(repository.existsById(id))
                .thenReturn(Boolean.FALSE);
        assertThrows(EntityNotFoundException.class, () ->
                service.editAll(list));
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
    void testFindByAlbumId() {
        final String albumId = UUID.randomUUID().toString();
        final Figurinha figurinha = mapper.parseEntity(buildFigurinhaDTO(albumId));
        Mockito.when(repository.findByAlbumId(albumId)).
                thenReturn((List.of(figurinha)));
        final List<Figurinha> response = service.findByAlbumId(albumId);
        assertEquals(1, response.size());
        assertEquals("1", response.get(0).getAlbumId());
        assertEquals(1, response.get(0).getFigurinhaPrototipo().getNumber());
    }

    private FigurinhaDTO buildFigurinhaDTO(String id) {
        return FigurinhaDTO.builder()
                .id(id)
                .figurinhaPrototipo(buildFigurinhaPrototipoDTO())
                .albumId("1")
                .build();
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

    private FigurinhaCreationDTO buildFigurinhaCreationDTO(String id) {
        return FigurinhaCreationDTO.builder()
                .figurinhaPrototipoId("1")
                .albumId("1")
                .build();
    }

    private FigurinhaUpdateDTO buildFigurinhaToSellDTO(String id) {
        return FigurinhaUpdateDTO.builder()
                .figurinhaPrototipoId("1")
                .albumId("1")
                .build();
    }

}