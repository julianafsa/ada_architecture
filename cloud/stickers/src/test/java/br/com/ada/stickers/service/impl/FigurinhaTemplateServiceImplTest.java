package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaDTO;
import br.com.ada.stickers.model.entity.PrototipoDeFigurinha;
import br.com.ada.stickers.model.mapper.PrototipoDeFigurinhaMapper;
import br.com.ada.stickers.repository.PrototipoDeFigurinhaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FigurinhaTemplateServiceImplTest {

  @Mock
  private PrototipoDeFigurinhaRepository repository;

  @Mock
  private PrototipoDeFigurinhaMapper mapper;

  @InjectMocks
  private PrototipoDeFigurinhaServiceImpl service;

  @Test
  public void findAll(){

    var actual = service.findAll(Optional.empty());

    assertNotNull(actual);

  }

  @Test
  public void findById(){

    Optional<PrototipoDeFigurinha> template = Optional.of(new PrototipoDeFigurinha());
    template.get().setId("1");

    var response = new PrototipoDeFigurinhaDTO();
    response.setId("1");

    when(repository.findById("1")).thenReturn(template);
    when(mapper.parseDTO(template.get())).thenReturn(response);

    var actual = service.findById("1");

    assertNotNull(actual);
  }


}
