package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.FigurinhaDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaDTO;
import br.com.ada.stickers.model.dto.TransacaoFigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.TransacaoFigurinhaDTO;
import br.com.ada.stickers.model.entity.TransacaoFigurinha;
import br.com.ada.stickers.model.mapper.FigurinhaMapper;
import br.com.ada.stickers.model.mapper.FigurinhaMapperImpl;
import br.com.ada.stickers.model.mapper.TransacaoFigurinhaMapper;
import br.com.ada.stickers.model.mapper.TransacaoFigurinhaMapperImpl;
import br.com.ada.stickers.repository.TransacaoFigurinhaRepository;
import br.com.ada.stickers.service.TransacaoFigurinhaService;
import br.com.ada.stickers.service.FigurinhaService;
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

    private TransacaoFigurinhaRepository repository;
    private TransacaoFigurinhaMapper mapper;
    private TransacaoFigurinhaService service;
    private FigurinhaService figurinhaService;
    private FigurinhaMapper figurinhaMapper;

    @BeforeEach
    public void init() {
        this.repository = mock(TransacaoFigurinhaRepository.class);
        this.mapper = new TransacaoFigurinhaMapperImpl();
        this.figurinhaService = mock(FigurinhaService.class);
        this.figurinhaMapper = new FigurinhaMapperImpl();
        this.service = new TransacaoFigurinhaServiceImpl(
                repository, mapper, figurinhaService, figurinhaMapper);
    }

    @Test
    void testFindAll() {
        final String id = UUID.randomUUID().toString();
        final TransacaoFigurinhaDTO transacaoFigurinhaDTO = buildStickerJournalDTO(id);
        Mockito.when(repository.findAll()).
                thenReturn((List.of(mapper.parseEntity(transacaoFigurinhaDTO))));
        final List<TransacaoFigurinhaDTO> response = service.findAll();
        assertEquals(1, response.size());
        assertEquals(id, response.get(0).getId());
        assertEquals("1", response.get(0).getSticker().getAlbumId());
        assertEquals(1, response.get(0).getSticker().getStickerTemplate().getNumber());
    }

    @Test
    void testFindById() {
        final String id = UUID.randomUUID().toString();
        final TransacaoFigurinhaDTO transacaoFigurinhaDTO = buildStickerJournalDTO(id);
        Mockito.when(repository.findById(id)).
                thenReturn(Optional.of(mapper.parseEntity(transacaoFigurinhaDTO)));
        final TransacaoFigurinhaDTO response = service.findById(id);
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
        final TransacaoFigurinhaCreationDTO transacaoFigurinhaCreationDTO = buildStickerJournalCreationDTO();
        final TransacaoFigurinha transacaoFigurinha = mapper.parseEntity(buildStickerJournalDTO(id));
        Mockito.when(repository.save(any()))
                .thenReturn(transacaoFigurinha);
        final TransacaoFigurinhaDTO response = service.create(transacaoFigurinhaCreationDTO);
        assertEquals(id, response.getId());
        assertEquals("1", response.getSticker().getAlbumId());
        assertEquals(1, response.getSticker().getStickerTemplate().getNumber());

    }

    private TransacaoFigurinhaDTO buildStickerJournalDTO(String id) {
        final TransacaoFigurinhaDTO transacaoFigurinhaDTO = TransacaoFigurinhaDTO.builder()
                .id(id)
                .sourceAlbumId("1")
                .destinationAlbumId("2")
                .sticker(buildStickerDTO())
                .date(LocalDateTime.now())
                .price(new BigDecimal(1))
                .build();
        return transacaoFigurinhaDTO;
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

    private TransacaoFigurinhaCreationDTO buildStickerJournalCreationDTO() {
        final TransacaoFigurinhaCreationDTO transacaoFigurinhaCreationDTO = TransacaoFigurinhaCreationDTO.builder()
                .sourceAlbumId("1")
                .destinationAlbumId("2")
                .sticker(figurinhaMapper.parseEntity(buildStickerDTO()))
                .price(new BigDecimal(1))
                .build();
        return transacaoFigurinhaCreationDTO;
    }


}