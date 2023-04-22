package br.com.ada.stickers.controller;

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

import br.com.ada.stickers.model.dto.StickerTemplateCreationDTO;
import br.com.ada.stickers.model.dto.StickerTemplateDTO;
import br.com.ada.stickers.model.dto.StickerTemplateUpdateDTO;
import br.com.ada.stickers.service.impl.StickerTemplateServiceImpl;

@SpringBootTest
public class StickerTemplateControllerTest {

  @Mock
  private StickerTemplateServiceImpl service;

  @InjectMocks
  private StickerTemplateController controller;

  @Test
  public void shouldRetrieveAllStickersTemplates() {

    List<StickerTemplateDTO> stickerTemplateDTOList = new ArrayList<>();
    StickerTemplateDTO stickerTemplateDTO = new StickerTemplateDTO();
    stickerTemplateDTOList.add(stickerTemplateDTO);

    Mockito.when(service.findAll(Optional.empty())).thenReturn(stickerTemplateDTOList);

    ResponseEntity<List<StickerTemplateDTO>> response = controller.findAll(Optional.empty());

    Mockito.verify(service).findAll(Optional.empty());
    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  public void shouldRetrieveOneStickerTemplateInformingItsId() {

    StickerTemplateDTO stickerTemplateDTO = new StickerTemplateDTO();
    Mockito.when(service.findById(anyString())).thenReturn(stickerTemplateDTO);

    ResponseEntity<StickerTemplateDTO> response = controller.findById("1");

    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  public void shouldCreateAnotherInstanceOfStickerTemplate() {

    StickerTemplateDTO stickerTemplateDTO = new StickerTemplateDTO();
    StickerTemplateCreationDTO stickerTemplateCreationDTO = new StickerTemplateCreationDTO();
    Mockito.when(service.create(stickerTemplateCreationDTO)).thenReturn(stickerTemplateDTO);

    ResponseEntity<StickerTemplateDTO> response = controller.create(stickerTemplateCreationDTO);

    Mockito.verify(service).create(stickerTemplateCreationDTO);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void shouldUpdateDataFromStickerTemplateInformingItsId(){
    StickerTemplateDTO stickerTemplateDTO = new StickerTemplateDTO();
    StickerTemplateUpdateDTO stickerTemplateUpdateDTO = new StickerTemplateUpdateDTO();
    Mockito.when(service.edit("1", stickerTemplateUpdateDTO)).thenReturn(stickerTemplateDTO);

    ResponseEntity<StickerTemplateDTO> response = controller.edit("1", stickerTemplateUpdateDTO);

    Mockito.verify(service).edit("1", stickerTemplateUpdateDTO);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldDeleteAStickerTemplateByInformingItsId(){

    ResponseEntity<Object> response = controller.delete("1");

    Mockito.verify(service).delete("1");
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
