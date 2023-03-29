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
import br.com.ada.albuns.model.entity.AlbumTemplate;
import br.com.ada.albuns.repository.AlbumJournalRepository;
import br.com.ada.albuns.repository.AlbumTemplateRepository;
import br.com.ada.albuns.service.AlbumService;

public class AlbumServiceWithJournalImplTest {
	private AlbumService albumService;
	private AlbumJournalRepository albumJournalRepository;
	private AlbumTemplateRepository albumTemplateRepository;
	private AlbumServiceWithJournalImpl albumServiceWithJournal;

	@BeforeEach
	public void setUp() {
		this.albumService = mock(AlbumService.class);
		this.albumJournalRepository = mock(AlbumJournalRepository.class);
		this.albumTemplateRepository = mock(AlbumTemplateRepository.class);
		this.albumServiceWithJournal = new AlbumServiceWithJournalImpl(albumService, albumJournalRepository, albumTemplateRepository);
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
		String albumTemplateId = "AlbumTemplateId";
		String albumTemplateName = "AlbumTemplateName";
		BigDecimal albumPrice = new BigDecimal("2.34");
		String userId = "UserId";
		AlbumDTO albumDTO = AlbumDTO.builder()
				.albumTemplateId(albumTemplateId)
				.userId(userId)
				.build();
		AlbumDTO savedAlbumDTO = AlbumDTO.builder()
				.id(albumId)
				.albumTemplateId(albumTemplateId)
				.userId(userId)
				.build();
		AlbumTemplate albumTemplate = AlbumTemplate.builder()
				.id(albumTemplateId)
				.name(albumTemplateName)
				.price(albumPrice)
				.build();
		ArgumentCaptor<AlbumJournal> albumJournalCaptor = ArgumentCaptor.forClass(AlbumJournal.class);

		when(albumService.create(albumDTO)).thenReturn(savedAlbumDTO);
		when(albumTemplateRepository.findById(albumTemplateId)).thenReturn(Optional.of(albumTemplate));
		
		// Act
		AlbumDTO actualAlbumDTO = albumServiceWithJournal.create(albumDTO);
		
		// Assert
		verify(albumJournalRepository).save(albumJournalCaptor.capture());
		AlbumJournal albumJournal = albumJournalCaptor.getValue();
		assertNull(albumJournal.getId());
		assertEquals(albumId, albumJournal.getAlbumId());
		assertEquals(albumTemplateId, albumJournal.getAlbumTemplateId());
		assertEquals(albumTemplateName, albumJournal.getAlbumTemplateName());
		assertNotNull(albumJournal.getDateTime());
		assertEquals(albumPrice, albumJournal.getPrice());
		assertEquals(userId, albumJournal.getUserId());
		
		assertEquals(albumId, actualAlbumDTO.getId());
		assertEquals(albumTemplateId, actualAlbumDTO.getAlbumTemplateId());
		assertEquals(userId, actualAlbumDTO.getUserId());
	}
	
	@Test
	public void testFindDefaultAlbum() {
		// Arrange
		String albumTemplateId = UUID.randomUUID().toString();
		AlbumDTO album = new AlbumDTO();
		when(albumService.findDefaultAlbum(albumTemplateId)).thenReturn(album);
		
		// Act
		AlbumDTO actualAlbum = albumServiceWithJournal.findDefaultAlbum(albumTemplateId);
		
		// Assert
		assertEquals(album, actualAlbum);
	}
}
