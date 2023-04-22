package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.StickerDTO;
import br.com.ada.stickers.model.dto.StickerJournalCreationDTO;
import br.com.ada.stickers.model.dto.StickerJournalDTO;
import br.com.ada.stickers.model.dto.StickerTemplateDTO;
import br.com.ada.stickers.model.entity.StickerJournal;
import br.com.ada.stickers.model.mapper.StickerJournalMapper;
import br.com.ada.stickers.model.mapper.StickerJournalMapperImpl;
import br.com.ada.stickers.model.mapper.StickerMapper;
import br.com.ada.stickers.model.mapper.StickerMapperImpl;
import br.com.ada.stickers.repository.StickerJournalRepository;
import br.com.ada.stickers.service.StickerJournalService;
import br.com.ada.stickers.service.StickerService;
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

class StickerJournalServiceImplTest {

    private StickerJournalRepository repository;
    private StickerJournalMapper mapper;
    private StickerJournalService service;
    private StickerService stickerService;
    private StickerMapper stickerMapper;

    @BeforeEach
    public void init() {
        this.repository = mock(StickerJournalRepository.class);
        this.mapper = new StickerJournalMapperImpl();
        this.stickerService = mock(StickerService.class);
        this.stickerMapper = new StickerMapperImpl();
        this.service = new StickerJournalServiceImpl(
                repository, mapper, stickerService, stickerMapper);
    }

    @Test
    void testFindAll() {
        final String id = UUID.randomUUID().toString();
        final StickerJournalDTO stickerJournalDTO = buildStickerJournalDTO(id);
        Mockito.when(repository.findAll()).
                thenReturn((List.of(mapper.parseEntity(stickerJournalDTO))));
        final List<StickerJournalDTO> response = service.findAll();
        assertEquals(1, response.size());
        assertEquals(id, response.get(0).getId());
        assertEquals("1", response.get(0).getSticker().getAlbumId());
        assertEquals(1, response.get(0).getSticker().getStickerTemplate().getNumber());
    }

    @Test
    void testFindById() {
        final String id = UUID.randomUUID().toString();
        final StickerJournalDTO stickerJournalDTO = buildStickerJournalDTO(id);
        Mockito.when(repository.findById(id)).
                thenReturn(Optional.of(mapper.parseEntity(stickerJournalDTO)));
        final StickerJournalDTO response = service.findById(id);
        assertEquals(id, response.getId());
        assertEquals("1", response.getSticker().getAlbumId());
        assertEquals(1, response.getSticker().getStickerTemplate().getNumber());
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
        final StickerJournalCreationDTO stickerJournalCreationDTO = buildStickerJournalCreationDTO();
        final StickerJournal stickerJournal = mapper.parseEntity(buildStickerJournalDTO(id));
        Mockito.when(repository.save(any()))
                .thenReturn(stickerJournal);
        final StickerJournalDTO response = service.create(stickerJournalCreationDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getSticker().getAlbumId());
        assertEquals(1, response.getSticker().getStickerTemplate().getNumber());

    }

    private StickerJournalDTO buildStickerJournalDTO(String id) {
        final StickerJournalDTO stickerJournalDTO = StickerJournalDTO.builder()
                .id(id)
                .sourceAlbumId("1")
                .destinationAlbumId("2")
                .sticker(buildStickerDTO())
                .date(LocalDateTime.now())
                .price(new BigDecimal(1))
                .build();
        return stickerJournalDTO;
    }

    private StickerDTO buildStickerDTO() {
        final StickerDTO stickerDTO = StickerDTO.builder()
                .id(UUID.randomUUID().toString())
                .stickerTemplate(buildStickerTemplateDTO())
                .albumId("1")
                .build();
        return stickerDTO;
    }

    private StickerTemplateDTO buildStickerTemplateDTO() {
        final StickerTemplateDTO stickerTemplateDTO = StickerTemplateDTO.builder()
                .id(UUID.randomUUID().toString())
                .albumTemplateId(UUID.randomUUID().toString())
                .number(1)
                .description("Sticker 1")
                .image("base64image")
                .rarity(1)
                .stickerPrice(new BigDecimal(1))
                .build();
        return stickerTemplateDTO;
    }

    private StickerJournalCreationDTO buildStickerJournalCreationDTO() {
        final StickerJournalCreationDTO stickerJournalCreationDTO = StickerJournalCreationDTO.builder()
                .sourceAlbumId("1")
                .destinationAlbumId("2")
                .sticker(stickerMapper.parseEntity(buildStickerDTO()))
                .price(new BigDecimal(1))
                .build();
        return stickerJournalCreationDTO;
    }


}