package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.FigurinhaDTO;
import br.com.ada.stickers.model.dto.FigurinhaUpdateDTO;
import br.com.ada.stickers.model.dto.FigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaDTO;
import br.com.ada.stickers.model.entity.Figurinha;
import br.com.ada.stickers.model.mapper.FigurinhaMapper;
import br.com.ada.stickers.model.mapper.FigurinhaMapperImpl;
import br.com.ada.stickers.model.mapper.PrototipoDeFigurinhaMapper;
import br.com.ada.stickers.model.mapper.PrototipoDeFigurinhaMapperImpl;
import br.com.ada.stickers.repository.FigurinhaRepository;
import br.com.ada.stickers.service.FigurinhaService;
import br.com.ada.stickers.service.PrototipoDeFigurinhaService;
import br.com.ada.stickers.service.producer.StickerErrorProducer;
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
    private PrototipoDeFigurinhaService prototipoDeFigurinhaService;
    private PrototipoDeFigurinhaMapper prototipoDeFigurinhaMapper;
    private StickerErrorProducer stickerErrorProducer;

    @BeforeEach
    public void init() {
        this.repository = mock(FigurinhaRepository.class);
        this.mapper = new FigurinhaMapperImpl();
        this.prototipoDeFigurinhaService = mock(PrototipoDeFigurinhaService.class);
        this.prototipoDeFigurinhaMapper = new PrototipoDeFigurinhaMapperImpl();
        this.stickerErrorProducer = mock(StickerErrorProducer.class);
        this.service = new FigurinhaServiceImpl(
                repository, mapper, prototipoDeFigurinhaService, prototipoDeFigurinhaMapper, stickerErrorProducer);
    }

    @Test
    void testFindAll() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaDTO figurinhaDTO = buildStickerDTO(id);
        Mockito.when(repository.findAll()).
                thenReturn((List.of(mapper.parseEntity(figurinhaDTO))));
        final List<FigurinhaDTO> response = service.findAll();
        assertEquals(1, response.size());
        assertEquals(id, response.get(0).getId());
        assertEquals("1", response.get(0).getAlbumId());
        assertEquals(1, response.get(0).getStickerTemplate().getNumber());
    }

    @Test
    void testFindById() {
        final String id = UUID.randomUUID().toString();
        final Figurinha figurinha = mapper.parseEntity(buildStickerDTO(id));
        Mockito.when(repository.findById(id)).
                thenReturn((Optional.of(figurinha)));
        final FigurinhaDTO response = service.findById(id);
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
        final FigurinhaCreationDTO figurinhaCreationDTO = buildStickerCreationDTO(id);
        final Figurinha figurinha = mapper.parseEntity(buildStickerDTO(id));
        Mockito.when(repository.save(any()))
                .thenReturn(figurinha);
        final FigurinhaDTO response = service.create(figurinhaCreationDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getAlbumId());
        assertEquals(1, response.getStickerTemplate().getNumber());
    }

    @Test
    void testEdit() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaDTO figurinhaDTO = buildStickerDTO(id);
        final Figurinha figurinha = mapper.parseEntity(figurinhaDTO);
        final FigurinhaUpdateDTO figurinhaUpdateDTO = buildStickerToSellDTO(id);
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(figurinha));
        Mockito.when(repository.save(figurinha))
                .thenReturn(figurinha);
        final Figurinha response = service.edit(id, figurinhaUpdateDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getAlbumId());
        assertEquals(1, response.getStickerTemplate().getNumber());
    }

    @Test
    void testEditIdNotFound() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaUpdateDTO figurinhaUpdateDTO = buildStickerToSellDTO(id);
        Mockito.when(repository.existsById(id))
                .thenReturn(Boolean.FALSE);
        assertThrows(EntityNotFoundException.class, () ->
                service.edit(id, figurinhaUpdateDTO));
    }

    @Test
    void testEditAll() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaDTO figurinhaDTO = buildStickerDTO(id);
        final Figurinha figurinha = mapper.parseEntity(figurinhaDTO);
        final List<Figurinha> list = List.of(figurinha);
        Mockito.when(repository.existsById(id))
                .thenReturn(Boolean.TRUE);
        Mockito.when(repository.saveAll(list))
                .thenReturn(list);
        final List<Figurinha> response = service.editAll(list);
        assertEquals(1, response.size());
        assertEquals("1", response.get(0).getAlbumId());
        assertEquals(1, response.get(0).getStickerTemplate().getNumber());
    }

    @Test
    void testEditAllNotFoundException() {
        final String id = UUID.randomUUID().toString();
        final FigurinhaDTO figurinhaDTO = buildStickerDTO(id);
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
        final Figurinha figurinha = mapper.parseEntity(buildStickerDTO(albumId));
        Mockito.when(repository.findByAlbumId(albumId)).
                thenReturn((List.of(figurinha)));
        final List<Figurinha> response = service.findByAlbumId(albumId);
        assertEquals(1, response.size());
        assertEquals("1", response.get(0).getAlbumId());
        assertEquals(1, response.get(0).getStickerTemplate().getNumber());
    }

    private FigurinhaDTO buildStickerDTO(String id) {
        return FigurinhaDTO.builder()
                .id(id)
                .stickerTemplate(buildStickerTemplateDTO())
                .albumId("1")
                .build();
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

    private FigurinhaCreationDTO buildStickerCreationDTO(String id) {
        return FigurinhaCreationDTO.builder()
                .stickerTemplateId("1")
                .albumId("1")
                .build();
    }

    private FigurinhaUpdateDTO buildStickerToSellDTO(String id) {
        return FigurinhaUpdateDTO.builder()
                .stickerTemplateId("1")
                .albumId("1")
                .build();
    }

}