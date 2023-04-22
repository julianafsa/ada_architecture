package br.com.ada.albuns.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ada.albuns.model.dto.AlbumJournalDTO;
import br.com.ada.albuns.model.entity.AlbumJournal;
import br.com.ada.albuns.model.mapper.AlbumJournalMapper;
import br.com.ada.albuns.model.mapper.AlbumJournalMapperImpl;
import br.com.ada.albuns.repository.AlbumJournalRepository;

public class AlbumJournalServiceImplTest {
	private AlbumJournalRepository repository;
	private AlbumJournalMapper mapper;
	private AlbumJournalServiceImpl service;

	@BeforeEach
	public void setUp() {
		this.repository = mock(AlbumJournalRepository.class);
		this.mapper = new AlbumJournalMapperImpl();
		this.service = new AlbumJournalServiceImpl(repository, mapper);
	}
	
	@Test
	public void testFindAll() {
		// Arrange
		Long id = 1L;
		String albumPrototipoId = "AlbumPrototipoId";
		String albumPrototipoName = "AlbumPrototipoName";
		String albumId = "AlbumId";
		String usuarioId = "UsuarioId";
		LocalDateTime dateTime = LocalDateTime.now();
		BigDecimal price = new BigDecimal("1.23");
		AlbumJournal album = AlbumJournal.builder()
				.id(id)
				.albumPrototipoId(albumPrototipoId)
				.albumPrototipoName(albumPrototipoName)
				.albumId(albumId)
				.usuarioId(usuarioId)
				.dateTime(dateTime)
				.price(price)
				.build();
		when(repository.findAll()).thenReturn(List.of(album));
		
		// Act
		List<AlbumJournalDTO> actualAlbumJournalsDTO = service.findAll();
		
		// Assert
		assertEquals(1, actualAlbumJournalsDTO.size());
		assertEquals(id, actualAlbumJournalsDTO.get(0).getId());
		assertEquals(albumPrototipoId, actualAlbumJournalsDTO.get(0).getAlbumPrototipoId());
		assertEquals(albumPrototipoName, actualAlbumJournalsDTO.get(0).getAlbumPrototipoName());
		assertEquals(albumId, actualAlbumJournalsDTO.get(0).getAlbumId());
		assertEquals(usuarioId, actualAlbumJournalsDTO.get(0).getUsuarioId());
		assertEquals(dateTime, actualAlbumJournalsDTO.get(0).getDateTime());
		assertEquals(price, actualAlbumJournalsDTO.get(0).getPrice());
	}
}
