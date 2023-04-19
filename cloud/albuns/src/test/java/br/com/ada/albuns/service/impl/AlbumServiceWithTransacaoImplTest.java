package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.entity.PrototipoDeAlbum;
import br.com.ada.albuns.model.entity.TransacaoAlbum;
import br.com.ada.albuns.repository.PrototipoDeAlbumRepository;
import br.com.ada.albuns.repository.TransacaoAlbumRepository;
import br.com.ada.albuns.service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlbumServiceWithTransacaoImplTest {
	private AlbumService albumService;
	private TransacaoAlbumRepository transacaoAlbumRepository;
	private PrototipoDeAlbumRepository prototipoDeAlbumRepository;
	private AlbumServiceWithTransacaoImpl albumServiceWithJournal;

	@BeforeEach
	public void setUp() {
		this.albumService = mock(AlbumService.class);
		this.transacaoAlbumRepository = mock(TransacaoAlbumRepository.class);
		this.prototipoDeAlbumRepository = mock(PrototipoDeAlbumRepository.class);
		this.albumServiceWithJournal = new AlbumServiceWithTransacaoImpl(albumService, transacaoAlbumRepository, prototipoDeAlbumRepository);
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
		PrototipoDeAlbum prototipoDeAlbum = PrototipoDeAlbum.builder()
				.id(albumTemplateId)
				.name(albumTemplateName)
				.price(albumPrice)
				.build();
		ArgumentCaptor<TransacaoAlbum> albumJournalCaptor = ArgumentCaptor.forClass(TransacaoAlbum.class);

		when(albumService.create(albumDTO)).thenReturn(savedAlbumDTO);
		when(prototipoDeAlbumRepository.findById(albumTemplateId)).thenReturn(Optional.of(prototipoDeAlbum));
		
		// Act
		AlbumDTO actualAlbumDTO = albumServiceWithJournal.create(albumDTO);
		
		// Assert
		verify(transacaoAlbumRepository).save(albumJournalCaptor.capture());
		TransacaoAlbum transacaoAlbum = albumJournalCaptor.getValue();
		assertNull(transacaoAlbum.getId());
		assertEquals(albumId, transacaoAlbum.getAlbumId());
		assertEquals(albumTemplateId, transacaoAlbum.getAlbumTemplateId());
		assertEquals(albumTemplateName, transacaoAlbum.getAlbumTemplateName());
		assertNotNull(transacaoAlbum.getDateTime());
		assertEquals(albumPrice, transacaoAlbum.getPrice());
		assertEquals(userId, transacaoAlbum.getUserId());
		
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
