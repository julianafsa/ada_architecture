package br.com.ada.figurinhas.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoUpdateDTO;
import br.com.ada.figurinhas.service.impl.FigurinhaPrototipoServiceImpl;

@SpringBootTest
public class FigurinhaPrototipoControllerTest {

  @Mock
  private FigurinhaPrototipoServiceImpl service;

  @InjectMocks
  private FigurinhaPrototipoController controller;

  @Test
  public void shouldRetrieveAllFigurinhasPrototipos() {

    List<FigurinhaPrototipoDTO> figurinhaPrototipoDTOList = new ArrayList<>();
    FigurinhaPrototipoDTO figurinhaPrototipoDTO = new FigurinhaPrototipoDTO();
    figurinhaPrototipoDTOList.add(figurinhaPrototipoDTO);

    Mockito.when(service.findAll(Optional.empty())).thenReturn(figurinhaPrototipoDTOList);

    ResponseEntity<List<FigurinhaPrototipoDTO>> response = controller.findAll(Optional.empty());

    Mockito.verify(service).findAll(Optional.empty());
    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  public void shouldRetrieveOneFigurinhaPrototipoInformingItsId() {

    FigurinhaPrototipoDTO figurinhaPrototipoDTO = new FigurinhaPrototipoDTO();
    Mockito.when(service.findById(anyString())).thenReturn(figurinhaPrototipoDTO);

    ResponseEntity<FigurinhaPrototipoDTO> response = controller.findById("1");

    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  public void shouldCreateAnotherInstanceOfFigurinhaPrototipo() {

    FigurinhaPrototipoDTO figurinhaPrototipoDTO = new FigurinhaPrototipoDTO();
    FigurinhaPrototipoCreationDTO figurinhaPrototipoCreationDTO = new FigurinhaPrototipoCreationDTO();
    Mockito.when(service.create(figurinhaPrototipoCreationDTO)).thenReturn(figurinhaPrototipoDTO);

    ResponseEntity<FigurinhaPrototipoDTO> response = controller.create(figurinhaPrototipoCreationDTO);

    Mockito.verify(service).create(figurinhaPrototipoCreationDTO);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void shouldUpdateDataFromFigurinhaPrototipoInformingItsId(){
    FigurinhaPrototipoDTO figurinhaPrototipoDTO = new FigurinhaPrototipoDTO();
    FigurinhaPrototipoUpdateDTO figurinhaPrototipoUpdateDTO = new FigurinhaPrototipoUpdateDTO();
    Mockito.when(service.edit("1", figurinhaPrototipoUpdateDTO)).thenReturn(figurinhaPrototipoDTO);

    ResponseEntity<FigurinhaPrototipoDTO> response = controller.edit("1", figurinhaPrototipoUpdateDTO);

    Mockito.verify(service).edit("1", figurinhaPrototipoUpdateDTO);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldDeleteAFigurinhaPrototipoByInformingItsId(){

    ResponseEntity<Object> response = controller.delete("1");

    Mockito.verify(service).delete("1");
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
