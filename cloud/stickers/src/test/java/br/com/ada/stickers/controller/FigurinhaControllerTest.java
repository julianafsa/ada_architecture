package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.Figurinha;
import br.com.ada.stickers.model.mapper.FigurinhaMapper;
import br.com.ada.stickers.service.FigurinhaService;
import br.com.ada.stickers.service.FigurinhaServiceWithTransacao;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class FigurinhaControllerTest {
    @Mock
    private FigurinhaService service;

    @Mock
    private FigurinhaServiceWithTransacao figurinhaServiceWithTransacao;

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
    public void buyStickerPackSuccess(){

        Mockito.when(figurinhaServiceWithTransacao.buyStickerPack(any())).thenReturn(Collections.singletonList(new Figurinha()));
        ResponseEntity<List<FigurinhaDTO>> response = figurinhaController.buyStickerPack(new FigurinhaBuyPackDTO());

        Mockito.verify(figurinhaServiceWithTransacao).buyStickerPack(new FigurinhaBuyPackDTO());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void buyStickerPackThrowException(){

        Mockito.when(figurinhaServiceWithTransacao.buyStickerPack(any())).thenThrow(RuntimeException.class);
        ResponseEntity<List<FigurinhaDTO>> response = figurinhaController.buyStickerPack(new FigurinhaBuyPackDTO());

        Mockito.verify(figurinhaServiceWithTransacao).buyStickerPack(new FigurinhaBuyPackDTO());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void buyStickerFromAlbumSuccess(){

        Mockito.when(figurinhaServiceWithTransacao.buyStickerFromAlbum(any())).thenReturn(new Figurinha());
        ResponseEntity<FigurinhaDTO> response = figurinhaController.buyStickerFromAlbum(new FigurinhaBuyFromAlbumDTO());

        Mockito.verify(figurinhaServiceWithTransacao).buyStickerFromAlbum(new FigurinhaBuyFromAlbumDTO());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void buyStickerFromAlbumThrowException (){

        Mockito.when(figurinhaServiceWithTransacao.buyStickerFromAlbum(any())).thenThrow(RuntimeException.class);
        ResponseEntity<FigurinhaDTO> response = figurinhaController.buyStickerFromAlbum(new FigurinhaBuyFromAlbumDTO());

        Mockito.verify(figurinhaServiceWithTransacao).buyStickerFromAlbum(new FigurinhaBuyFromAlbumDTO());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}