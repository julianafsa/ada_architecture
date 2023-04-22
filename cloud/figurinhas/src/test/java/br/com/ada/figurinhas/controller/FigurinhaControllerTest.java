package br.com.ada.figurinhas.controller;

import br.com.ada.figurinhas.model.dto.*;
import br.com.ada.figurinhas.model.entity.Figurinha;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.service.FigurinhaService;
import br.com.ada.figurinhas.service.FigurinhaServiceWithJournal;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class FigurinhaControllerTest {
    @Mock
    private  FigurinhaService service;

    @Mock
    private FigurinhaServiceWithJournal figurinhaServiceWithJournal;

    @Mock
    private FigurinhaMapper mapper;

    @InjectMocks
    private FigurinhaController figurinhaController;

    @Test
    public void findAllTestSuccess(){
        List<FigurinhaDTO> figurinhaDTOList = new ArrayList<>();
        figurinhaDTOList.add(new FigurinhaDTO());
        Mockito.when(service.findAll()).thenReturn(figurinhaDTOList);

        ResponseEntity<List<FigurinhaDTO>> response = figurinhaController.findAll();

        Mockito.verify(service).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void findAllTestErrorNoContent(){
        List<FigurinhaDTO> figurinhaDTOList = new ArrayList<>();
        Mockito.when(service.findAll()).thenReturn(figurinhaDTOList);

        ResponseEntity<List<FigurinhaDTO>> response = figurinhaController.findAll();

        Mockito.verify(service).findAll();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    public void findByIdSuccess(){
        FigurinhaDTO figurinhaDTO = new FigurinhaDTO();

        Mockito.when(service.findById(any())).thenReturn(figurinhaDTO);

        ResponseEntity<FigurinhaDTO> response = figurinhaController.findById("dummy");

        Mockito.verify(service).findById("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void findByIdThrowEntityNotFoundException(){
        Mockito.when(service.findById(any())).thenThrow(EntityNotFoundException.class);

        ResponseEntity<FigurinhaDTO> response = figurinhaController.findById("dummy");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void findByIdThrowException(){
        Mockito.when(service.findById(any())).thenThrow(RuntimeException.class);

        ResponseEntity<FigurinhaDTO> response = figurinhaController.findById("dummy");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createTestSuccess(){
        FigurinhaCreationDTO figurinhaCreationDTO = new FigurinhaCreationDTO();

        Mockito.when(service.create(any())).thenReturn(new FigurinhaDTO());
        ResponseEntity<FigurinhaDTO> response = figurinhaController.create(figurinhaCreationDTO);

        Mockito.verify(service).create(figurinhaCreationDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    public void createTestThrowException(){
        FigurinhaCreationDTO figurinhaCreationDTO = new FigurinhaCreationDTO();

        Mockito.when(service.create(any())).thenThrow(RuntimeException.class);
        ResponseEntity<FigurinhaDTO> response = figurinhaController.create(figurinhaCreationDTO);

        Mockito.verify(service).create(figurinhaCreationDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void updateTestSuccess(){
        FigurinhaUpdateDTO figurinhaUpdateDTO = new FigurinhaUpdateDTO();

        Mockito.when(service.edit(any(), any())).thenReturn(new Figurinha());
        ResponseEntity<FigurinhaDTO> response = figurinhaController.edit("dummy", figurinhaUpdateDTO);

        Mockito.verify(service).edit("dummy", figurinhaUpdateDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }


    @Test
    public void updateTestThrowException(){
        FigurinhaUpdateDTO figurinhaUpdateDTO = new FigurinhaUpdateDTO();

        Mockito.when(service.edit(any(), any())).thenThrow(NullPointerException.class);
        ResponseEntity<FigurinhaDTO> response = figurinhaController.edit("dummy", figurinhaUpdateDTO);

        Mockito.verify(service).edit("dummy", figurinhaUpdateDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void updateTestThrowEntityNotFoundException(){
        FigurinhaUpdateDTO figurinhaUpdateDTO = new FigurinhaUpdateDTO();

        Mockito.when(service.edit(any(), any())).thenThrow(EntityNotFoundException.class);
        ResponseEntity<FigurinhaDTO> response = figurinhaController.edit("dummy", figurinhaUpdateDTO);

        Mockito.verify(service).edit("dummy", figurinhaUpdateDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void deleteTestSuccess(){
        FigurinhaUpdateDTO figurinhaUpdateDTO = new FigurinhaUpdateDTO();

        ResponseEntity<Object> response = figurinhaController.delete("dummy");

        Mockito.verify(service).delete("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void deleteTestEntityNotFoundException(){

        Mockito.doThrow(EntityNotFoundException.class).when(service).delete("dummy");

        ResponseEntity<Object> response = figurinhaController.delete("dummy");

        Mockito.verify(service).delete("dummy");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void deleteTestException(){

        Mockito.doThrow(RuntimeException.class).when(service).delete("dummy");

        ResponseEntity<Object> response = figurinhaController.delete("dummy");

        Mockito.verify(service).delete("dummy");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void buyFigurinhaPacoteSuccess(){

        Mockito.when(figurinhaServiceWithJournal.buyFigurinhaPacote(any())).thenReturn(Collections.singletonList(new Figurinha()));
        ResponseEntity<List<FigurinhaDTO>> response = figurinhaController.buyFigurinhaPacote(new FigurinhaBuyPacoteDTO());

        Mockito.verify(figurinhaServiceWithJournal).buyFigurinhaPacote(new FigurinhaBuyPacoteDTO());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void buyFigurinhaPacoteThrowException(){

        Mockito.when(figurinhaServiceWithJournal.buyFigurinhaPacote(any())).thenThrow(RuntimeException.class);
        ResponseEntity<List<FigurinhaDTO>> response = figurinhaController.buyFigurinhaPacote(new FigurinhaBuyPacoteDTO());

        Mockito.verify(figurinhaServiceWithJournal).buyFigurinhaPacote(new FigurinhaBuyPacoteDTO());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void buyFigurinhaFromAlbumSuccess(){

        Mockito.when(figurinhaServiceWithJournal.buyFigurinhaFromAlbum(any())).thenReturn(new Figurinha());
        ResponseEntity<FigurinhaDTO> response = figurinhaController.buyFigurinhaFromAlbum(new FigurinhaBuyFromAlbumDTO());

        Mockito.verify(figurinhaServiceWithJournal).buyFigurinhaFromAlbum(new FigurinhaBuyFromAlbumDTO());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void buyFigurinhaFromAlbumThrowException (){

        Mockito.when(figurinhaServiceWithJournal.buyFigurinhaFromAlbum(any())).thenThrow(RuntimeException.class);
        ResponseEntity<FigurinhaDTO> response = figurinhaController.buyFigurinhaFromAlbum(new FigurinhaBuyFromAlbumDTO());

        Mockito.verify(figurinhaServiceWithJournal).buyFigurinhaFromAlbum(new FigurinhaBuyFromAlbumDTO());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}