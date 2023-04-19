package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.TransacaoFigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.TransacaoFigurinhaDTO;
import br.com.ada.stickers.service.TransacaoFigurinhaService;
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
class TransacaoFigurinhaControllerTest {

    @Mock
    private TransacaoFigurinhaService service;

    @InjectMocks
    private TransacaoFigurinhaController transacaoFigurinhaController;

    @Test
    void findAll() {
        List<TransacaoFigurinhaDTO> transacaoFigurinhaDTOList = new ArrayList<>();
        transacaoFigurinhaDTOList.add(new TransacaoFigurinhaDTO());
        Mockito.when(service.findAll()).thenReturn(transacaoFigurinhaDTOList);

        ResponseEntity<List<TransacaoFigurinhaDTO>> response = transacaoFigurinhaController.findAll();

        Mockito.verify(service).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById() {
        TransacaoFigurinhaDTO transacaoFigurinhaDTO = new TransacaoFigurinhaDTO();

        Mockito.when(service.findById(any())).thenReturn(transacaoFigurinhaDTO);

        ResponseEntity<TransacaoFigurinhaDTO> response = transacaoFigurinhaController.findById("dummy");

        Mockito.verify(service).findById("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create() {

        Mockito.when(service.create(any())).thenReturn(new TransacaoFigurinhaDTO());
        ResponseEntity<TransacaoFigurinhaDTO> response = transacaoFigurinhaController.create(new TransacaoFigurinhaCreationDTO());

        Mockito.verify(service).create(new TransacaoFigurinhaCreationDTO() );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}