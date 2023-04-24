package br.com.ada.figurinhas.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoDTO;
import br.com.ada.figurinhas.model.entity.FigurinhaPrototipo;
import br.com.ada.figurinhas.model.mapper.FigurinhaPrototipoMapper;
import br.com.ada.figurinhas.repository.FigurinhaPrototipoRepository;

@SpringBootTest
public class FigurinhaPrototipoServiceImplTest {

  @Mock
  private FigurinhaPrototipoRepository repository;

  @Mock
  private FigurinhaPrototipoMapper mapper;

  @InjectMocks
  private FigurinhaPrototipoServiceImpl service;

  @Test
  public void findAll(){

    var actual = service.findAll(Optional.empty());

    assertNotNull(actual);

  }

  @Test
  public void findById(){

    Optional<FigurinhaPrototipo> prototipo = Optional.of(new FigurinhaPrototipo());
    prototipo.get().setId("1");

    var response = new FigurinhaPrototipoDTO();
    response.setId("1");

    when(repository.findById("1")).thenReturn(prototipo);
    when(mapper.parseDTO(prototipo.get())).thenReturn(response);

    var actual = service.findById("1");

    assertNotNull(actual);
  }


}
