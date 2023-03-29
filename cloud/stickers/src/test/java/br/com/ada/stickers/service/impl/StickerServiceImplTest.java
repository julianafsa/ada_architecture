package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.Sticker;
import br.com.ada.stickers.model.mapper.StickerMapper;
import br.com.ada.stickers.model.mapper.StickerMapperImpl;
import br.com.ada.stickers.model.mapper.StickerTemplateMapper;
import br.com.ada.stickers.model.mapper.StickerTemplateMapperImpl;
import br.com.ada.stickers.repository.StickerRepository;
import br.com.ada.stickers.service.StickerService;
import br.com.ada.stickers.service.StickerTemplateService;
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

class StickerServiceImplTest {

    private StickerRepository repository;
    private StickerMapper mapper;
    private StickerService service;
    private StickerTemplateService stickerTemplateService;
    private StickerTemplateMapper stickerTemplateMapper;

    @BeforeEach
    public void init() {
        this.repository = mock(StickerRepository.class);
        this.mapper = new StickerMapperImpl();
        this.stickerTemplateService = mock(StickerTemplateService.class);
        this.stickerTemplateMapper = new StickerTemplateMapperImpl();
        this.service = new StickerServiceImpl(
                repository, mapper, stickerTemplateService, stickerTemplateMapper);
    }

    @Test
    void testFindAll() {
        final String id = UUID.randomUUID().toString();
        final StickerDTO stickerDTO = buildStickerDTO(id);
        Mockito.when(repository.findAll()).
                thenReturn((List.of(mapper.parseEntity(stickerDTO))));
        final List<StickerDTO> response = service.findAll();
        assertEquals(1, response.size());
        assertEquals(id, response.get(0).getId());
        assertEquals("1", response.get(0).getAlbumId());
        assertEquals(1, response.get(0).getStickerTemplate().getNumber());
    }

    @Test
    void testFindById() {
        final String id = UUID.randomUUID().toString();
        final Sticker sticker = mapper.parseEntity(buildStickerDTO(id));
        Mockito.when(repository.findById(id)).
                thenReturn((Optional.of(sticker)));
        final StickerDTO response = service.findById(id);
        assertEquals(id, response.getId());
        assertEquals("1", response.getAlbumId());
        assertEquals(1, response.getStickerTemplate().getNumber());
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
        final StickerCreationDTO stickerCreationDTO = buildStickerCreationDTO(id);
        final Sticker sticker = mapper.parseEntity(buildStickerDTO(id));
        Mockito.when(repository.save(any()))
                .thenReturn(sticker);
        final StickerDTO response = service.create(stickerCreationDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getAlbumId());
        assertEquals(1, response.getStickerTemplate().getNumber());
    }

    @Test
    void testEdit() {
        final String id = UUID.randomUUID().toString();
        final StickerDTO stickerDTO = buildStickerDTO(id);
        final Sticker sticker = mapper.parseEntity(stickerDTO);
        final StickerUpdateDTO stickerUpdateDTO = buildStickerToSellDTO(id);
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(sticker));
        Mockito.when(repository.save(sticker))
                .thenReturn(sticker);
        final Sticker response = service.edit(id, stickerUpdateDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getAlbumId());
        assertEquals(1, response.getStickerTemplate().getNumber());
    }

    @Test
    void testEditIdNotFound() {
        final String id = UUID.randomUUID().toString();
        final StickerUpdateDTO stickerUpdateDTO = buildStickerToSellDTO(id);
        Mockito.when(repository.existsById(id))
                .thenReturn(Boolean.FALSE);
        assertThrows(EntityNotFoundException.class, () ->
                service.edit(id, stickerUpdateDTO));
    }

    @Test
    void testEditAll() {
        final String id = UUID.randomUUID().toString();
        final StickerDTO stickerDTO = buildStickerDTO(id);
        final Sticker sticker = mapper.parseEntity(stickerDTO);
        final List<Sticker> list = List.of(sticker);
        Mockito.when(repository.existsById(id))
                .thenReturn(Boolean.TRUE);
        Mockito.when(repository.saveAll(list))
                .thenReturn(list);
        final List<Sticker> response = service.editAll(list);
        assertEquals(1, response.size());
        assertEquals("1", response.get(0).getAlbumId());
        assertEquals(1, response.get(0).getStickerTemplate().getNumber());
    }

    @Test
    void testEditAllNotFoundException() {
        final String id = UUID.randomUUID().toString();
        final StickerDTO stickerDTO = buildStickerDTO(id);
        final Sticker sticker = mapper.parseEntity(stickerDTO);
        final List<Sticker> list = List.of(sticker);
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
        final Sticker sticker = mapper.parseEntity(buildStickerDTO(albumId));
        Mockito.when(repository.findByAlbumId(albumId)).
                thenReturn((List.of(sticker)));
        final List<Sticker> response = service.findByAlbumId(albumId);
        assertEquals(1, response.size());
        assertEquals("1", response.get(0).getAlbumId());
        assertEquals(1, response.get(0).getStickerTemplate().getNumber());
    }

    private StickerDTO buildStickerDTO(String id) {
        final StickerDTO stickerDTO = StickerDTO.builder()
                .id(id)
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

    private StickerCreationDTO buildStickerCreationDTO(String id) {
        final StickerCreationDTO stickerCreationDTO = StickerCreationDTO.builder()
                .stickerTemplateId("1")
                .albumId("1")
                .build();
        return stickerCreationDTO;
    }

    private StickerUpdateDTO buildStickerToSellDTO(String id) {
        final StickerUpdateDTO stickerUpdateDTO = StickerUpdateDTO.builder()
                .stickerTemplateId("1")
                .albumId("1")
                .build();
        return stickerUpdateDTO;
    }

}