package br.com.ada.stickers.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ada.stickers.model.dto.StickerTemplateDTO;
import br.com.ada.stickers.model.entity.StickerTemplate;
import br.com.ada.stickers.model.mapper.StickerTemplateMapper;
import br.com.ada.stickers.repository.StickerTemplateRepository;

@SpringBootTest
public class StickerTemplateServiceImplTest {

  @Mock
  private StickerTemplateRepository repository;

  @Mock
  private StickerTemplateMapper mapper;

  @InjectMocks
  private StickerTemplateServiceImpl service;

  @Test
  public void findAll(){

    var actual = service.findAll(Optional.empty());

    assertNotNull(actual);

  }

  @Test
  public void findById(){

    Optional<StickerTemplate> template = Optional.of(new StickerTemplate());
    template.get().setId("1");

    var response = new StickerTemplateDTO();
    response.setId("1");

    when(repository.findById("1")).thenReturn(template);
    when(mapper.parseDTO(template.get())).thenReturn(response);

    var actual = service.findById("1");

    assertNotNull(actual);
  }


}
