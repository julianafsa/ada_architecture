package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.FigurinhaAVenda;
import br.com.ada.stickers.model.mapper.FigurinhaAVendaMapper;
import br.com.ada.stickers.service.FigurinhaAVendaService;
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
class FigurinhaAVendaControllerTest {

    @Mock
    private FigurinhaAVendaMapper mapper;

    @Mock
    private FigurinhaAVendaService service;

    @InjectMocks
    private FigurinhaAVendaController figurinhaAVendaController;

    @Test
    void findAll() {
        List<FigurinhaAVendaDTO> stickerJournalDTOList = new ArrayList<>();
        stickerJournalDTOList.add(new FigurinhaAVendaDTO());
        Mockito.when(service.findAll()).thenReturn(stickerJournalDTOList);

        ResponseEntity<List<FigurinhaAVendaDTO>> response = figurinhaAVendaController.findAll();

        Mockito.verify(service).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById() {
        FigurinhaAVendaDTO figurinhaAVendaDTO = new FigurinhaAVendaDTO();

        Mockito.when(service.findById(any())).thenReturn(new FigurinhaAVenda());

        ResponseEntity<FigurinhaAVendaDTO> response = figurinhaAVendaController.findById("dummy");

        Mockito.verify(service).findById("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create() {
        Mockito.when(service.create(any())).thenReturn(new FigurinhaAVendaDTO());
        ResponseEntity<FigurinhaAVendaDTO> response = figurinhaAVendaController.create(new FigurinhaAVendaCreationDTO());

        Mockito.verify(service).create(new FigurinhaAVendaCreationDTO());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void edit() {
        FigurinhaAVendaUpdateDTO figurinhaAVendaUpdateDTO = new FigurinhaAVendaUpdateDTO();

        Mockito.when(service.edit(any(), any())).thenReturn(new FigurinhaAVendaDTO());
        ResponseEntity<FigurinhaAVendaDTO> response = figurinhaAVendaController.edit("dummy", new FigurinhaAVendaUpdateDTO());

        Mockito.verify(service).edit("dummy", figurinhaAVendaUpdateDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void delete() {

        ResponseEntity<Object> response = figurinhaAVendaController.delete("dummy");

        Mockito.verify(service).delete("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}