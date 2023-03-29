package br.com.ada.albuns.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.mapper.AlbumMapper;
import br.com.ada.albuns.model.mapper.AlbumMapperImpl;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.service.StickerService;
import jakarta.persistence.EntityNotFoundException;

public class AlbumServiceImplTest {
	private AlbumRepository repository;
	private AlbumMapper mapper;
	private StickerService stickerService;
	private AlbumServiceImpl service;

	@BeforeEach
	public void setUp() {
		this.repository = mock(AlbumRepository.class);
		this.mapper = new AlbumMapperImpl();
		this.stickerService = mock(StickerService.class);
		this.service = new AlbumServiceImpl(repository, mapper, stickerService);
	}
	
	@Test
	public void testFindAll() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String albumTemplateId = "AlbumTemplateId";
		String userId = "UserId";
		Album album = Album.builder()
				.id(id)
				.albumTemplateId(albumTemplateId)
				.userId(userId)
				.build();
		when(repository.findAll()).thenReturn(List.of(album));
		
		// Act
		List<AlbumDTO> actualAlbums = service.findAll();
		
		// Assert
		assertEquals(1, actualAlbums.size());
		assertEquals(id, actualAlbums.get(0).getId());
		assertEquals(albumTemplateId, actualAlbums.get(0).getAlbumTemplateId());
		assertEquals(userId, actualAlbums.get(0).getUserId());
	}

	@Test
	public void testFindById() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String albumTemplateId = "AlbumTemplateId";
		String userId = "UserId";
		Album album = Album.builder()
				.id(id)
				.albumTemplateId(albumTemplateId)
				.userId(userId)
				.build();
		when(repository.findById(id)).thenReturn(Optional.of(album));
		
		// Act
		AlbumDTO actualAlbum = service.findById(id);
		
		// Assert
		assertEquals(id, actualAlbum.getId());
		assertEquals(albumTemplateId, actualAlbum.getAlbumTemplateId());
		assertEquals(userId, actualAlbum.getUserId());
	}

	@Test
	public void testFindByIdNotFound() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(repository.findById(id)).thenReturn(Optional.empty());
		
		// Act/Assert
		assertThrows(EntityNotFoundException.class, () -> service.findById(id));
	}

	@Test
	public void testCreate() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String albumTemplateId = "AlbumTemplateId";
		String userId = "UserId";
		AlbumDTO albumDTO = AlbumDTO.builder()
				.albumTemplateId(albumTemplateId)
				.userId(userId)
				.build();
		Album savedAlbum = Album.builder()
				.id(id)
				.albumTemplateId(albumTemplateId)
				.userId(userId)
				.build();

		when(repository.save(any())).thenReturn(savedAlbum);
		
		// Act
		AlbumDTO actualAlbumDTO = service.create(albumDTO);
		
		// Assert
		assertEquals(id, actualAlbumDTO.getId());
		assertEquals(albumTemplateId, actualAlbumDTO.getAlbumTemplateId());
		assertEquals(userId, actualAlbumDTO.getUserId());
	}
	
	@Test
	public void testFindDefaultAlbum() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String albumTemplateId = "AlbumTemplateId";
		Album album = Album.builder()
				.id(id)
				.albumTemplateId(albumTemplateId)
				.userId(null)
				.build();
		
		when(repository.findByUserIdAndAlbumTemplateId(null, albumTemplateId)).thenReturn(Optional.of(album));
		
		// Act
		AlbumDTO actualAlbumDTO = service.findDefaultAlbum(albumTemplateId);
		
		// Assert
		assertEquals(id, actualAlbumDTO.getId());
		assertEquals(albumTemplateId, actualAlbumDTO.getAlbumTemplateId());
		assertNull(actualAlbumDTO.getUserId());
	}

	@Test
	public void testFindDefaultAlbumNotFound() {
		// Arrange
		String albumTemplateId = "AlbumTemplateId";
		when(repository.findByUserIdAndAlbumTemplateId(null, albumTemplateId)).thenReturn(Optional.empty());
		
		// Act/Assert
		assertThrows(EntityNotFoundException.class, () -> service.findDefaultAlbum(albumTemplateId));
	}
}
