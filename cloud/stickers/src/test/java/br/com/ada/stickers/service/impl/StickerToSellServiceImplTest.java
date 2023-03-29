package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.StickerToSell;
import br.com.ada.stickers.model.mapper.StickerMapper;
import br.com.ada.stickers.model.mapper.StickerMapperImpl;
import br.com.ada.stickers.model.mapper.StickerToSellMapper;
import br.com.ada.stickers.model.mapper.StickerToSellMapperImpl;
import br.com.ada.stickers.repository.StickerToSellRepository;
import br.com.ada.stickers.service.StickerService;
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

class StickerToSellServiceImplTest {

    private StickerToSellRepository repository;
    private StickerToSellMapper mapper;
    private StickerToSellServiceImpl service;
    private StickerService stickerService;
    private StickerMapper stickerMapper;

    @BeforeEach
    public void init() {
        this.repository = mock(StickerToSellRepository.class);
        this.mapper = new StickerToSellMapperImpl();
        this.stickerService = mock(StickerService.class);
        this.stickerMapper = new StickerMapperImpl();
        this.service = new StickerToSellServiceImpl(
                repository, mapper, stickerService, stickerMapper);
    }

    @Test
    void testFindAll() {
        final String id = UUID.randomUUID().toString();
        final StickerToSellDTO stickerToSellDTO = buildStickerToSellDTO(id);
        Mockito.when(repository.findAll()).
                thenReturn((List.of(mapper.parseEntity(stickerToSellDTO))));
        final List<StickerToSellDTO> response = service.findAll();
        assertEquals(1, response.size());
        assertEquals(id, response.get(0).getId());
        assertEquals("1", response.get(0).getSticker().getAlbumId());
        assertEquals(1, response.get(0).getSticker().getStickerTemplate().getNumber());
    }

    @Test
    void testeFindById() {
        final String id = UUID.randomUUID().toString();
        final StickerToSell stickerToSell = mapper.parseEntity(buildStickerToSellDTO(id));
        Mockito.when(repository.findById(id)).
                thenReturn((Optional.of(stickerToSell)));
        final StickerToSell response = service.findById(id);
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
        final StickerToSellCreationDTO stickerCreationDTO = buildStickerToSellCreationDTO();
        final StickerToSellDTO stickerToSellDTO = buildStickerToSellDTO(id);
        Mockito.when(repository.save(any()))
                .thenReturn(mapper.parseEntity(stickerToSellDTO));
        final StickerToSellDTO response = service.create(stickerCreationDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getSticker().getAlbumId());
        assertEquals(1, response.getSticker().getStickerTemplate().getNumber());
    }

    @Test
    void testEdit() {
        final String id = UUID.randomUUID().toString();
        final StickerToSellDTO stickerToSellDTO = buildStickerToSellDTO(id);
        final StickerToSell stickerToSell = mapper.parseEntity(stickerToSellDTO);
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(stickerToSell));
        Mockito.when(repository.save(stickerToSell))
                .thenReturn(stickerToSell);
        final StickerToSellDTO response = service.edit(id, buildStickerToSellUpdateDTO());
        assertEquals(id, response.getId());
        assertEquals("1", response.getSticker().getAlbumId());
        assertEquals(1, response.getSticker().getStickerTemplate().getNumber());
    }

    @Test
    void testEditIdNotFound() {
        final String id = UUID.randomUUID().toString();
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
                service.edit(id, buildStickerToSellUpdateDTO()));
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
    void deleteByStickerId() {
        final String stickerId = UUID.randomUUID().toString();
        Mockito.when(repository.findByStickerId(stickerId))
                .thenReturn(Optional.of(mapper.parseEntity(buildStickerToSellDTO(stickerId))));
        service.deleteByStickerId(stickerId);
        verify(repository, times(1)).deleteById(stickerId);
    }

    @Test
    void deleteByStickerIdNotFound() {
        final String stickerId = UUID.randomUUID().toString();
        Mockito.when(repository.findByStickerId(stickerId))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.deleteByStickerId(stickerId));
    }

    @Test
    void findByStickerId() {
        final String stickerId = UUID.randomUUID().toString();
        Mockito.when(repository.findByStickerId(stickerId)).
                thenReturn(Optional.of(mapper.parseEntity(buildStickerToSellDTO(stickerId))));
        final Optional<StickerToSell> response = service.findByStickerId(stickerId);
        assertEquals(stickerId, response.get().getId());
        assertEquals("1", response.get().getSticker().getAlbumId());
        assertEquals(1, response.get().getSticker().getStickerTemplate().getNumber());
    }

    private StickerToSellDTO buildStickerToSellDTO(String id) {
        final StickerToSellDTO stickerToSellDTO = StickerToSellDTO.builder()
                .id(id)
                .sticker(buildStickerDTO())
                .price(new BigDecimal(1))
                .build();
        return stickerToSellDTO;
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

    private StickerToSellCreationDTO buildStickerToSellCreationDTO() {
        final StickerMapper stickerMapper = new StickerMapperImpl();
        final StickerToSellCreationDTO stickerToSellCreationDTO = StickerToSellCreationDTO.builder()
                .sticker(stickerMapper.parseEntity(buildStickerDTO()))
                .price(new BigDecimal(1))
                .build();
        return stickerToSellCreationDTO;
    }

    private StickerToSellUpdateDTO buildStickerToSellUpdateDTO() {
        final StickerToSellUpdateDTO stickerToSellUpdateDTO = StickerToSellUpdateDTO.builder()
                .price(new BigDecimal(1))
                .build();
        return stickerToSellUpdateDTO;
    }

}