package br.com.ada.figurinhas.controller;

import br.com.ada.figurinhas.model.dto.*;
import br.com.ada.figurinhas.model.entity.FigurinhaToSell;
import br.com.ada.figurinhas.model.mapper.FigurinhaToSellMapper;
import br.com.ada.figurinhas.service.FigurinhaToSellService;
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
class FigurinhaToSellControllerTest {

    @Mock
    private  FigurinhaToSellMapper mapper;

    @Mock
    private FigurinhaToSellService service;

    @InjectMocks
    private FigurinhaToSellController figurinhaToSellController;

    @Test
    void findAll() {
        List<FigurinhaToSellDTO> figurinhaJournalDTOList = new ArrayList<>();
        figurinhaJournalDTOList.add(new FigurinhaToSellDTO());
        Mockito.when(service.findAll()).thenReturn(figurinhaJournalDTOList);

        ResponseEntity<List<FigurinhaToSellDTO>> response = figurinhaToSellController.findAll();

        Mockito.verify(service).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById() {
        FigurinhaToSellDTO figurinhaToSellDTO = new FigurinhaToSellDTO();

        Mockito.when(service.findById(any())).thenReturn(new FigurinhaToSell());

        ResponseEntity<FigurinhaToSellDTO> response = figurinhaToSellController.findById("dummy");

        Mockito.verify(service).findById("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create() {
        Mockito.when(service.create(any())).thenReturn(new FigurinhaToSellDTO());
        ResponseEntity<FigurinhaToSellDTO> response = figurinhaToSellController.create(new FigurinhaToSellCreationDTO());

        Mockito.verify(service).create(new FigurinhaToSellCreationDTO());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void edit() {
        FigurinhaToSellUpdateDTO figurinhaToSellUpdateDTO = new FigurinhaToSellUpdateDTO();

        Mockito.when(service.edit(any(), any())).thenReturn(new FigurinhaToSellDTO());
        ResponseEntity<FigurinhaToSellDTO> response = figurinhaToSellController.edit("dummy", new FigurinhaToSellUpdateDTO());

        Mockito.verify(service).edit("dummy", figurinhaToSellUpdateDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void delete() {

        ResponseEntity<Object> response = figurinhaToSellController.delete("dummy");

        Mockito.verify(service).delete("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}