package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.Sticker;
import br.com.ada.stickers.model.mapper.StickerMapper;
import br.com.ada.stickers.service.StickerService;
import br.com.ada.stickers.service.StickerServiceWithJournal;
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
class StickerControllerTest {
    @Mock
    private  StickerService service;

    @Mock
    private StickerServiceWithJournal stickerServiceWithJournal;

    @Mock
    private StickerMapper mapper;

    @InjectMocks
    private StickerController stickerController;

    @Test
    public void findAllTestSuccess(){
        List<StickerDTO> stickerDTOList = new ArrayList<>();
        stickerDTOList.add(new StickerDTO());
        Mockito.when(service.findAll()).thenReturn(stickerDTOList);

        ResponseEntity<List<StickerDTO>> response = stickerController.findAll();

        Mockito.verify(service).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void findAllTestErrorNoContent(){
        List<StickerDTO> stickerDTOList = new ArrayList<>();
        Mockito.when(service.findAll()).thenReturn(stickerDTOList);

        ResponseEntity<List<StickerDTO>> response = stickerController.findAll();

        Mockito.verify(service).findAll();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    public void findByIdSuccess(){
        StickerDTO stickerDTO = new StickerDTO();

        Mockito.when(service.findById(any())).thenReturn(stickerDTO);

        ResponseEntity<StickerDTO> response = stickerController.findById("dummy");

        Mockito.verify(service).findById("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void findByIdThrowEntityNotFoundException(){
        Mockito.when(service.findById(any())).thenThrow(EntityNotFoundException.class);

        ResponseEntity<StickerDTO> response = stickerController.findById("dummy");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void findByIdThrowException(){
        Mockito.when(service.findById(any())).thenThrow(RuntimeException.class);

        ResponseEntity<StickerDTO> response = stickerController.findById("dummy");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createTestSuccess(){
        StickerCreationDTO stickerCreationDTO = new StickerCreationDTO();

        Mockito.when(service.create(any())).thenReturn(new StickerDTO());
        ResponseEntity<StickerDTO> response = stickerController.create(stickerCreationDTO);

        Mockito.verify(service).create(stickerCreationDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    public void createTestThrowException(){
        StickerCreationDTO stickerCreationDTO = new StickerCreationDTO();

        Mockito.when(service.create(any())).thenThrow(RuntimeException.class);
        ResponseEntity<StickerDTO> response = stickerController.create(stickerCreationDTO);

        Mockito.verify(service).create(stickerCreationDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void updateTestSuccess(){
        StickerUpdateDTO stickerUpdateDTO = new StickerUpdateDTO();

        Mockito.when(service.edit(any(), any())).thenReturn(new Sticker());
        ResponseEntity<StickerDTO> response = stickerController.edit("dummy", stickerUpdateDTO);

        Mockito.verify(service).edit("dummy", stickerUpdateDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }


    @Test
    public void updateTestThrowException(){
        StickerUpdateDTO stickerUpdateDTO = new StickerUpdateDTO();

        Mockito.when(service.edit(any(), any())).thenThrow(NullPointerException.class);
        ResponseEntity<StickerDTO> response = stickerController.edit("dummy", stickerUpdateDTO);

        Mockito.verify(service).edit("dummy", stickerUpdateDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void updateTestThrowEntityNotFoundException(){
        StickerUpdateDTO stickerUpdateDTO = new StickerUpdateDTO();

        Mockito.when(service.edit(any(), any())).thenThrow(EntityNotFoundException.class);
        ResponseEntity<StickerDTO> response = stickerController.edit("dummy", stickerUpdateDTO);

        Mockito.verify(service).edit("dummy", stickerUpdateDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void deleteTestSuccess(){
        StickerUpdateDTO stickerUpdateDTO = new StickerUpdateDTO();

        ResponseEntity<Object> response = stickerController.delete("dummy");

        Mockito.verify(service).delete("dummy");
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void deleteTestEntityNotFoundException(){

        Mockito.doThrow(EntityNotFoundException.class).when(service).delete("dummy");

        ResponseEntity<Object> response = stickerController.delete("dummy");

        Mockito.verify(service).delete("dummy");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void deleteTestException(){

        Mockito.doThrow(RuntimeException.class).when(service).delete("dummy");

        ResponseEntity<Object> response = stickerController.delete("dummy");

        Mockito.verify(service).delete("dummy");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void buyStickerPackSuccess(){

        Mockito.when(stickerServiceWithJournal.buyStickerPack(any())).thenReturn(Collections.singletonList(new Sticker()));
        ResponseEntity<List<StickerDTO>> response = stickerController.buyStickerPack(new StickerBuyPackDTO());

        Mockito.verify(stickerServiceWithJournal).buyStickerPack(new StickerBuyPackDTO());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void buyStickerPackThrowException(){

        Mockito.when(stickerServiceWithJournal.buyStickerPack(any())).thenThrow(RuntimeException.class);
        ResponseEntity<List<StickerDTO>> response = stickerController.buyStickerPack(new StickerBuyPackDTO());

        Mockito.verify(stickerServiceWithJournal).buyStickerPack(new StickerBuyPackDTO());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void buyStickerFromAlbumSuccess(){

        Mockito.when(stickerServiceWithJournal.buyStickerFromAlbum(any())).thenReturn(new Sticker());
        ResponseEntity<StickerDTO> response = stickerController.buyStickerFromAlbum(new StickerBuyFromAlbumDTO());

        Mockito.verify(stickerServiceWithJournal).buyStickerFromAlbum(new StickerBuyFromAlbumDTO());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void buyStickerFromAlbumThrowException (){

        Mockito.when(stickerServiceWithJournal.buyStickerFromAlbum(any())).thenThrow(RuntimeException.class);
        ResponseEntity<StickerDTO> response = stickerController.buyStickerFromAlbum(new StickerBuyFromAlbumDTO());

        Mockito.verify(stickerServiceWithJournal).buyStickerFromAlbum(new StickerBuyFromAlbumDTO());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}