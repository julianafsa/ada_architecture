package br.com.ada.albuns.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.entity.AlbumJournal;
import br.com.ada.albuns.model.entity.AlbumPrototipo;
import br.com.ada.albuns.repository.AlbumJournalRepository;
import br.com.ada.albuns.repository.AlbumPrototipoRepository;
import br.com.ada.albuns.service.AlbumService;

public class AlbumServiceWithJournalImplTest {
	private AlbumService albumService;
	private AlbumJournalRepository albumJournalRepository;
	private AlbumPrototipoRepository albumPrototipoRepository;
	private AlbumServiceWithJournalImpl albumServiceWithJournal;

	@BeforeEach
	public void setUp() {
		this.albumService = mock(AlbumService.class);
		this.albumJournalRepository = mock(AlbumJournalRepository.class);
		this.albumPrototipoRepository = mock(AlbumPrototipoRepository.class);
		this.albumServiceWithJournal = new AlbumServiceWithJournalImpl(albumService, albumJournalRepository, albumPrototipoRepository);
	}
	
	@Test
	public void testFindAll() {
		// Arrange
		List<AlbumDTO> albums = List.of(new AlbumDTO());
		when(albumService.findAll()).thenReturn(albums);
		
		// Act
		List<AlbumDTO> actualAlbums = albumServiceWithJournal.findAll();
		
		// Assert
		assertEquals(albums, actualAlbums);
	}

	@Test
	public void testFindById() {
		// Arrange
		String id = UUID.randomUUID().toString();
		AlbumDTO album = new AlbumDTO();
		when(albumService.findById(id)).thenReturn(album);
		
		// Act
		AlbumDTO actualAlbum = albumServiceWithJournal.findById(id);
		
		// Assert
		assertEquals(album, actualAlbum);
	}

	@Test
	public void testCreate() {
		// Arrange
		String albumId = UUID.randomUUID().toString();
		String albumPrototipoId = "AlbumPrototipoId";
		String albumPrototipoName = "AlbumPrototipoName";
		BigDecimal albumPrice = new BigDecimal("2.34");
		String usuarioId = "UsuarioId";
		AlbumDTO albumDTO = AlbumDTO.builder()
				.albumPrototipoId(albumPrototipoId)
				.usuarioId(usuarioId)
				.build();
		AlbumDTO savedAlbumDTO = AlbumDTO.builder()
				.id(albumId)
				.albumPrototipoId(albumPrototipoId)
				.usuarioId(usuarioId)
				.build();
		AlbumPrototipo albumPrototipo = AlbumPrototipo.builder()
				.id(albumPrototipoId)
				.name(albumPrototipoName)
				.price(albumPrice)
				.build();
		ArgumentCaptor<AlbumJournal> albumJournalCaptor = ArgumentCaptor.forClass(AlbumJournal.class);

		when(albumService.create(albumDTO)).thenReturn(savedAlbumDTO);
		when(albumPrototipoRepository.findById(albumPrototipoId)).thenReturn(Optional.of(albumPrototipo));
		
		// Act
		AlbumDTO actualAlbumDTO = albumServiceWithJournal.create(albumDTO);
		
		// Assert
		verify(albumJournalRepository).save(albumJournalCaptor.capture());
		AlbumJournal albumJournal = albumJournalCaptor.getValue();
		assertNull(albumJournal.getId());
		assertEquals(albumId, albumJournal.getAlbumId());
		assertEquals(albumPrototipoId, albumJournal.getAlbumPrototipoId());
		assertEquals(albumPrototipoName, albumJournal.getAlbumPrototipoName());
		assertNotNull(albumJournal.getDateTime());
		assertEquals(albumPrice, albumJournal.getPrice());
		assertEquals(usuarioId, albumJournal.getUsuarioId());
		
		assertEquals(albumId, actualAlbumDTO.getId());
		assertEquals(albumPrototipoId, actualAlbumDTO.getAlbumPrototipoId());
		assertEquals(usuarioId, actualAlbumDTO.getUsuarioId());
	}
	
	@Test
	public void testFindPadraoAlbum() {
		// Arrange
		String albumPrototipoId = UUID.randomUUID().toString();
		AlbumDTO album = new AlbumDTO();
		when(albumService.findPadraoAlbum(albumPrototipoId)).thenReturn(album);
		
		// Act
		AlbumDTO actualAlbum = albumServiceWithJournal.findPadraoAlbum(albumPrototipoId);
		
		// Assert
		assertEquals(album, actualAlbum);
	}
}
