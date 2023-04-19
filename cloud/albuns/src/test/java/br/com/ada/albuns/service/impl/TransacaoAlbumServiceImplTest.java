package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.model.dto.TransacaoAlbumDTO;
import br.com.ada.albuns.model.entity.TransacaoAlbum;
import br.com.ada.albuns.model.mapper.TransacaoAlbumMapper;
import br.com.ada.albuns.model.mapper.TransacaoAlbumMapperImpl;
import br.com.ada.albuns.repository.TransacaoAlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransacaoAlbumServiceImplTest {
	private TransacaoAlbumRepository repository;
	private TransacaoAlbumMapper mapper;
	private TransacaoAlbumServiceImpl service;

	@BeforeEach
	public void setUp() {
		this.repository = mock(TransacaoAlbumRepository.class);
		this.mapper = new TransacaoAlbumMapperImpl();
		this.service = new TransacaoAlbumServiceImpl(repository, mapper);
	}
	
	@Test
	public void testFindAll() {
		// Arrange
		Long id = 1L;
		String albumTemplateId = "AlbumTemplateId";
		String albumTemplateName = "AlbumTemplateName";
		String albumId = "AlbumId";
		String userId = "UserId";
		LocalDateTime dateTime = LocalDateTime.now();
		BigDecimal price = new BigDecimal("1.23");
		TransacaoAlbum album = TransacaoAlbum.builder()
				.id(id)
				.albumTemplateId(albumTemplateId)
				.albumTemplateName(albumTemplateName)
				.albumId(albumId)
				.userId(userId)
				.dateTime(dateTime)
				.price(price)
				.build();
		when(repository.findAll()).thenReturn(List.of(album));
		
		// Act
		List<TransacaoAlbumDTO> actualAlbumJournalsDTO = service.findAll();
		
		// Assert
		assertEquals(1, actualAlbumJournalsDTO.size());
		assertEquals(id, actualAlbumJournalsDTO.get(0).getId());
		assertEquals(albumTemplateId, actualAlbumJournalsDTO.get(0).getAlbumTemplateId());
		assertEquals(albumTemplateName, actualAlbumJournalsDTO.get(0).getAlbumTemplateName());
		assertEquals(albumId, actualAlbumJournalsDTO.get(0).getAlbumId());
		assertEquals(userId, actualAlbumJournalsDTO.get(0).getUserId());
		assertEquals(dateTime, actualAlbumJournalsDTO.get(0).getDateTime());
		assertEquals(price, actualAlbumJournalsDTO.get(0).getPrice());
	}
}
