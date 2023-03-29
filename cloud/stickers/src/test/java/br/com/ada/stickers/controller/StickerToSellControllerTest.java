package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.StickerToSell;
import br.com.ada.stickers.model.mapper.StickerToSellMapper;
import br.com.ada.stickers.service.StickerToSellService;
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
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class StickerToSellControllerTest {

    @Mock
    private  StickerToSellMapper mapper;

    @Mock
    private StickerToSellService service;

    @InjectMocks
    private StickerToSellController stickerToSellController;

    @Test
    void findAll() {
        List<StickerToSellDTO> stickerJournalDTOList = new ArrayList<>();
        stickerJournalDTOList.add(new StickerToSellDTO());
        Mockito.when(service.findAll()).thenReturn(stickerJournalDTOList);

        ResponseEntity<List<StickerToSellDTO>> response = stickerToSellController.findAll();

        Mockito.verify(service).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById() {
        StickerToSellDTO stickerToSellDTO = new StickerToSellDTO();

        Mockito.when(service.findById(any())).thenReturn(new StickerToSell());

        ResponseEntity<StickerToSellDTO> response = stickerToSellController.findById("dummy");

        Mockito.verify(service).findById("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create() {
        Mockito.when(service.create(any())).thenReturn(new StickerToSellDTO());
        ResponseEntity<StickerToSellDTO> response = stickerToSellController.create(new StickerToSellCreationDTO());

        Mockito.verify(service).create(new StickerToSellCreationDTO());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void edit() {
        StickerToSellUpdateDTO stickerToSellUpdateDTO = new StickerToSellUpdateDTO();

        Mockito.when(service.edit(any(), any())).thenReturn(new StickerToSellDTO());
        ResponseEntity<StickerToSellDTO> response = stickerToSellController.edit("dummy", new StickerToSellUpdateDTO());

        Mockito.verify(service).edit("dummy", stickerToSellUpdateDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void delete() {

        ResponseEntity<Object> response = stickerToSellController.delete("dummy");

        Mockito.verify(service).delete("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}