package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaUpdateDTO;
import br.com.ada.stickers.service.impl.PrototipoDeFigurinhaServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class PrototipoDeFigurinhaControllerTest {

  @Mock
  private PrototipoDeFigurinhaServiceImpl service;

  @InjectMocks
  private PrototipoDeFigurinhaController controller;

  @Test
  public void shouldRetrieveAllStickersTemplates() {

    List<PrototipoDeFigurinhaDTO> prototipoDeFigurinhaDTOList = new ArrayList<>();
    PrototipoDeFigurinhaDTO prototipoDeFigurinhaDTO = new PrototipoDeFigurinhaDTO();
    prototipoDeFigurinhaDTOList.add(prototipoDeFigurinhaDTO);

    Mockito.when(service.findAll(Optional.empty())).thenReturn(prototipoDeFigurinhaDTOList);

    ResponseEntity<List<PrototipoDeFigurinhaDTO>> response = controller.findAll(Optional.empty());

    Mockito.verify(service).findAll(Optional.empty());
    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  public void shouldRetrieveOneStickerTemplateInformingItsId() {

    PrototipoDeFigurinhaDTO prototipoDeFigurinhaDTO = new PrototipoDeFigurinhaDTO();
    Mockito.when(service.findById(anyString())).thenReturn(prototipoDeFigurinhaDTO);

    ResponseEntity<PrototipoDeFigurinhaDTO> response = controller.findById("1");

    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  public void shouldCreateAnotherInstanceOfStickerTemplate() {

    PrototipoDeFigurinhaDTO prototipoDeFigurinhaDTO = new PrototipoDeFigurinhaDTO();
    PrototipoDeFigurinhaCreationDTO prototipoDeFigurinhaCreationDTO = new PrototipoDeFigurinhaCreationDTO();
    Mockito.when(service.create(prototipoDeFigurinhaCreationDTO)).thenReturn(prototipoDeFigurinhaDTO);

    ResponseEntity<PrototipoDeFigurinhaDTO> response = controller.create(prototipoDeFigurinhaCreationDTO);

    Mockito.verify(service).create(prototipoDeFigurinhaCreationDTO);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void shouldUpdateDataFromStickerTemplateInformingItsId(){
    PrototipoDeFigurinhaDTO prototipoDeFigurinhaDTO = new PrototipoDeFigurinhaDTO();
    PrototipoDeFigurinhaUpdateDTO prototipoDeFigurinhaUpdateDTO = new PrototipoDeFigurinhaUpdateDTO();
    Mockito.when(service.edit("1", prototipoDeFigurinhaUpdateDTO)).thenReturn(prototipoDeFigurinhaDTO);

    ResponseEntity<PrototipoDeFigurinhaDTO> response = controller.edit("1", prototipoDeFigurinhaUpdateDTO);

    Mockito.verify(service).edit("1", prototipoDeFigurinhaUpdateDTO);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldDeleteAStickerTemplateByInformingItsId(){

    ResponseEntity<Object> response = controller.delete("1");

    Mockito.verify(service).delete("1");
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
