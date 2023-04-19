package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.FigurinhaAVenda;
import br.com.ada.stickers.model.mapper.FigurinhaAVendaMapper;
import br.com.ada.stickers.model.mapper.FigurinhaAVendaMapperImpl;
import br.com.ada.stickers.model.mapper.FigurinhaMapper;
import br.com.ada.stickers.model.mapper.FigurinhaMapperImpl;
import br.com.ada.stickers.repository.FigurinhaAVendaRepository;
import br.com.ada.stickers.service.FigurinhaService;
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

    private FigurinhaAVendaRepository repository;
    private FigurinhaAVendaMapper mapper;
    private FigurinhaAVendaServiceImpl service;
    private FigurinhaService figurinhaService;
    private FigurinhaMapper figurinhaMapper;

    @BeforeEach
    public void init() {
        this.repository = mock(FigurinhaAVendaRepository.class);
        this.mapper = new FigurinhaAVendaMapperImpl();
        this.figurinhaService = mock(FigurinhaService.class);
        this.figurinhaMapper = new FigurinhaMapperImpl();
        this.service = new FigurinhaAVendaServiceImpl(
                repository, mapper, figurinhaService, figurinhaMapper);
    }

    @Test
    void testFindAll() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaAVendaDTO figurinhaAVendaDTO = buildStickerToSellDTO(id);
        Mockito.when(repository.findAll()).
                thenReturn((List.of(mapper.parseEntity(figurinhaAVendaDTO))));
        final List<FigurinhaAVendaDTO> response = service.findAll();
        assertEquals(1, response.size());
        assertEquals(id, response.get(0).getId());
        assertEquals("1", response.get(0).getSticker().getAlbumId());
        assertEquals(1, response.get(0).getSticker().getStickerTemplate().getNumber());
    }

    @Test
    void testeFindById() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaAVenda figurinhaAVenda = mapper.parseEntity(buildStickerToSellDTO(id));
        Mockito.when(repository.findById(id)).
                thenReturn((Optional.of(figurinhaAVenda)));
        final FigurinhaAVenda response = service.findById(id);
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
        final FigurinhaAVendaCreationDTO stickerCreationDTO = buildStickerToSellCreationDTO();
        final FigurinhaAVendaDTO figurinhaAVendaDTO = buildStickerToSellDTO(id);
        Mockito.when(repository.save(any()))
                .thenReturn(mapper.parseEntity(figurinhaAVendaDTO));
        final FigurinhaAVendaDTO response = service.create(stickerCreationDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getSticker().getAlbumId());
        assertEquals(1, response.getSticker().getStickerTemplate().getNumber());
    }

    @Test
    void testEdit() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaAVendaDTO figurinhaAVendaDTO = buildStickerToSellDTO(id);
        final FigurinhaAVenda figurinhaAVenda = mapper.parseEntity(figurinhaAVendaDTO);
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(figurinhaAVenda));
        Mockito.when(repository.save(figurinhaAVenda))
                .thenReturn(figurinhaAVenda);
        final FigurinhaAVendaDTO response = service.edit(id, buildStickerToSellUpdateDTO());
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
        final Optional<FigurinhaAVenda> response = service.findByStickerId(stickerId);
        assertEquals(stickerId, response.get().getId());
        assertEquals("1", response.get().getSticker().getAlbumId());
        assertEquals(1, response.get().getSticker().getStickerTemplate().getNumber());
    }

    private FigurinhaAVendaDTO buildStickerToSellDTO(String id) {
        final FigurinhaAVendaDTO figurinhaAVendaDTO = FigurinhaAVendaDTO.builder()
                .id(id)
                .sticker(buildStickerDTO())
                .price(new BigDecimal(1))
                .build();
        return figurinhaAVendaDTO;
    }

    private FigurinhaDTO buildStickerDTO() {
        final FigurinhaDTO figurinhaDTO = FigurinhaDTO.builder()
                .id(UUID.randomUUID().toString())
                .stickerTemplate(buildStickerTemplateDTO())
                .albumId("1")
                .build();
        return figurinhaDTO;
    }

    private PrototipoDeFigurinhaDTO buildStickerTemplateDTO() {
        final PrototipoDeFigurinhaDTO prototipoDeFigurinhaDTO = PrototipoDeFigurinhaDTO.builder()
                .id(UUID.randomUUID().toString())
                .albumTemplateId(UUID.randomUUID().toString())
                .number(1)
                .description("Sticker 1")
                .image("base64image")
                .rarity(1)
                .stickerPrice(new BigDecimal(1))
                .build();
        return prototipoDeFigurinhaDTO;
    }

    private FigurinhaAVendaCreationDTO buildStickerToSellCreationDTO() {
        final FigurinhaMapper figurinhaMapper = new FigurinhaMapperImpl();
        final FigurinhaAVendaCreationDTO figurinhaAVendaCreationDTO = FigurinhaAVendaCreationDTO.builder()
                .sticker(figurinhaMapper.parseEntity(buildStickerDTO()))
                .price(new BigDecimal(1))
                .build();
        return figurinhaAVendaCreationDTO;
    }

    private FigurinhaAVendaUpdateDTO buildStickerToSellUpdateDTO() {
        final FigurinhaAVendaUpdateDTO figurinhaAVendaUpdateDTO = FigurinhaAVendaUpdateDTO.builder()
                .price(new BigDecimal(1))
                .build();
        return figurinhaAVendaUpdateDTO;
    }

}