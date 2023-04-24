package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.model.dto.FigurinhaDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaJournalCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaJournalDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoDTO;
import br.com.ada.figurinhas.model.entity.FigurinhaJournal;
import br.com.ada.figurinhas.model.mapper.FigurinhaJournalMapper;
import br.com.ada.figurinhas.model.mapper.FigurinhaJournalMapperImpl;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapperImpl;
import br.com.ada.figurinhas.repository.FigurinhaJournalRepository;
import br.com.ada.figurinhas.service.FigurinhaJournalService;
import br.com.ada.figurinhas.service.FigurinhaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class FigurinhaJournalServiceImplTest {

    private FigurinhaJournalRepository repository;
    private FigurinhaJournalMapper mapper;
    private FigurinhaJournalService service;
    private FigurinhaService figurinhaService;
    private FigurinhaMapper figurinhaMapper;

    @BeforeEach
    public void init() {
        this.repository = mock(FigurinhaJournalRepository.class);
        this.mapper = new FigurinhaJournalMapperImpl();
        this.figurinhaService = mock(FigurinhaService.class);
        this.figurinhaMapper = new FigurinhaMapperImpl();
        this.service = new FigurinhaJournalServiceImpl(
                repository, mapper, figurinhaService, figurinhaMapper);
    }

    @Test
    void testFindAll() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaJournalDTO figurinhaJournalDTO = buildFigurinhaJournalDTO(id);
        Mockito.when(repository.findAll()).
                thenReturn((List.of(mapper.parseEntity(figurinhaJournalDTO))));
        final List<FigurinhaJournalDTO> response = service.findAll();
        assertEquals(1, response.size());
        assertEquals(id, response.get(0).getId());
        assertEquals("1", response.get(0).getFigurinha().getAlbumId());
        assertEquals(1, response.get(0).getFigurinha().getFigurinhaPrototipo().getNumber());
    }

    @Test
    void testFindById() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaJournalDTO figurinhaJournalDTO = buildFigurinhaJournalDTO(id);
        Mockito.when(repository.findById(id)).
                thenReturn(Optional.of(mapper.parseEntity(figurinhaJournalDTO)));
        final FigurinhaJournalDTO response = service.findById(id);
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
        final FigurinhaJournalCreationDTO figurinhaJournalCreationDTO = buildFigurinhaJournalCreationDTO();
        final FigurinhaJournal figurinhaJournal = mapper.parseEntity(buildFigurinhaJournalDTO(id));
        Mockito.when(repository.save(any()))
                .thenReturn(figurinhaJournal);
        final FigurinhaJournalDTO response = service.create(figurinhaJournalCreationDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getFigurinha().getAlbumId());
        assertEquals(1, response.getFigurinha().getFigurinhaPrototipo().getNumber());

    }

    private FigurinhaJournalDTO buildFigurinhaJournalDTO(String id) {
        final FigurinhaJournalDTO figurinhaJournalDTO = FigurinhaJournalDTO.builder()
                .id(id)
                .sourceAlbumId("1")
                .destinationAlbumId("2")
                .figurinha(buildFigurinhaDTO())
                .date(LocalDateTime.now())
                .price(new BigDecimal(1))
                .build();
        return figurinhaJournalDTO;
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

    private FigurinhaJournalCreationDTO buildFigurinhaJournalCreationDTO() {
        final FigurinhaJournalCreationDTO figurinhaJournalCreationDTO = FigurinhaJournalCreationDTO.builder()
                .sourceAlbumId("1")
                .destinationAlbumId("2")
                .figurinha(figurinhaMapper.parseEntity(buildFigurinhaDTO()))
                .price(new BigDecimal(1))
                .build();
        return figurinhaJournalCreationDTO;
    }


}