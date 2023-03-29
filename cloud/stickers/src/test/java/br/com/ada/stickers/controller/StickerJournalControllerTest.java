package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.StickerCreationDTO;
import br.com.ada.stickers.model.dto.StickerDTO;
import br.com.ada.stickers.model.dto.StickerJournalCreationDTO;
import br.com.ada.stickers.model.dto.StickerJournalDTO;
import br.com.ada.stickers.service.StickerJournalService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class StickerJournalControllerTest {

    @Mock
    private StickerJournalService service;

    @InjectMocks
    private StickerJournalController stickerJournalController;

    @Test
    void findAll() {
        List<StickerJournalDTO> stickerJournalDTOList = new ArrayList<>();
        stickerJournalDTOList.add(new StickerJournalDTO());
        Mockito.when(service.findAll()).thenReturn(stickerJournalDTOList);

        ResponseEntity<List<StickerJournalDTO>> response = stickerJournalController.findAll();

        Mockito.verify(service).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById() {
        StickerJournalDTO stickerJournalDTO = new StickerJournalDTO();

        Mockito.when(service.findById(any())).thenReturn(stickerJournalDTO);

        ResponseEntity<StickerJournalDTO> response = stickerJournalController.findById("dummy");

        Mockito.verify(service).findById("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create() {

        Mockito.when(service.create(any())).thenReturn(new StickerJournalDTO());
        ResponseEntity<StickerJournalDTO> response = stickerJournalController.create(new StickerJournalCreationDTO());

        Mockito.verify(service).create(new StickerJournalCreationDTO() );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}