package br.com.ada.figurinhas.controller;

import br.com.ada.figurinhas.model.dto.FigurinhaCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaJournalCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaJournalDTO;
import br.com.ada.figurinhas.service.FigurinhaJournalService;
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
class FigurinhaJournalControllerTest {

    @Mock
    private FigurinhaJournalService service;

    @InjectMocks
    private FigurinhaJournalController figurinhaJournalController;

    @Test
    void findAll() {
        List<FigurinhaJournalDTO> figurinhaJournalDTOList = new ArrayList<>();
        figurinhaJournalDTOList.add(new FigurinhaJournalDTO());
        Mockito.when(service.findAll()).thenReturn(figurinhaJournalDTOList);

        ResponseEntity<List<FigurinhaJournalDTO>> response = figurinhaJournalController.findAll();

        Mockito.verify(service).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById() {
        FigurinhaJournalDTO figurinhaJournalDTO = new FigurinhaJournalDTO();

        Mockito.when(service.findById(any())).thenReturn(figurinhaJournalDTO);

        ResponseEntity<FigurinhaJournalDTO> response = figurinhaJournalController.findById("dummy");

        Mockito.verify(service).findById("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create() {

        Mockito.when(service.create(any())).thenReturn(new FigurinhaJournalDTO());
        ResponseEntity<FigurinhaJournalDTO> response = figurinhaJournalController.create(new FigurinhaJournalCreationDTO());

        Mockito.verify(service).create(new FigurinhaJournalCreationDTO() );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}