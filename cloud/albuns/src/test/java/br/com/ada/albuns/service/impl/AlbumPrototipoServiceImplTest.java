package br.com.ada.albuns.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import br.com.ada.albuns.model.dto.AlbumTemplateDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.entity.AlbumTemplate;
import br.com.ada.albuns.model.mapper.AlbumTemplateMapper;
import br.com.ada.albuns.model.mapper.AlbumTemplateMapperImpl;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.repository.AlbumTemplateRepository;
import jakarta.persistence.EntityNotFoundException;

public class AlbumTemplateServiceImplTest {
	private AlbumTemplateRepository repository;
	private AlbumTemplateMapper mapper;
	private AlbumRepository albumRepository;
	private AlbumTemplateServiceImpl service;

	@BeforeEach
	public void setUp() {
		this.repository = mock(AlbumTemplateRepository.class);
		this.mapper = new AlbumTemplateMapperImpl();
		this.albumRepository = mock(AlbumRepository.class);
		this.service = new AlbumTemplateServiceImpl(repository, mapper, albumRepository);
	}
	
	@Test
	public void testFindAll() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String cover = "Cover";
		LocalDateTime expirationDate = LocalDateTime.of(2023, 12, 31, 23, 59); 
		LocalDateTime launchDate = LocalDateTime.of(2023, 1, 1, 0, 0); 
		String name = "Name";
		Long numStickers = 10L;
		BigDecimal price = new BigDecimal("10.99");
		AlbumTemplate albumTemplate = AlbumTemplate.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();
		when(repository.findAll()).thenReturn(List.of(albumTemplate));
		
		// Act
		List<AlbumTemplateDTO> actualAlbumTemplates = service.findAll();
		
		// Assert
		assertEquals(1, actualAlbumTemplates.size());
		AlbumTemplateDTO actualAlbumTemplate = actualAlbumTemplates.get(0);
		assertEquals(id, actualAlbumTemplate.getId());
		assertEquals(cover, actualAlbumTemplate.getCover());
		assertEquals(expirationDate, actualAlbumTemplate.getExpirationDate());
		assertEquals(launchDate, actualAlbumTemplate.getLaunchDate());
		assertEquals(name, actualAlbumTemplate.getName());
		assertEquals(numStickers, actualAlbumTemplate.getNumStickers());
		assertEquals(price, actualAlbumTemplate.getPrice());
	}

	@Test
	public void testFindById() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String cover = "Cover";
		LocalDateTime expirationDate = LocalDateTime.of(2023, 12, 31, 23, 59); 
		LocalDateTime launchDate = LocalDateTime.of(2023, 1, 1, 0, 0); 
		String name = "Name";
		Long numStickers = 10L;
		BigDecimal price = new BigDecimal("10.99");
		AlbumTemplate albumTemplate = AlbumTemplate.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();
		when(repository.findById(id)).thenReturn(Optional.of(albumTemplate));
		
		// Act
		AlbumTemplateDTO actualAlbumTemplate = service.findById(id);
		
		// Assert
		assertEquals(id, actualAlbumTemplate.getId());
		assertEquals(cover, actualAlbumTemplate.getCover());
		assertEquals(expirationDate, actualAlbumTemplate.getExpirationDate());
		assertEquals(launchDate, actualAlbumTemplate.getLaunchDate());
		assertEquals(name, actualAlbumTemplate.getName());
		assertEquals(numStickers, actualAlbumTemplate.getNumStickers());
		assertEquals(price, actualAlbumTemplate.getPrice());
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
		String cover = "Cover";
		LocalDateTime expirationDate = LocalDateTime.of(2023, 12, 31, 23, 59); 
		LocalDateTime launchDate = LocalDateTime.of(2023, 1, 1, 0, 0); 
		String name = "Name";
		Long numStickers = 10L;
		BigDecimal price = new BigDecimal("10.99");
		AlbumTemplateDTO albumTemplateDTO = AlbumTemplateDTO.builder()
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();
		AlbumTemplate savedAlbumTemplate = AlbumTemplate.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();

		when(repository.save(any())).thenReturn(savedAlbumTemplate);
		
		// Act
		AlbumTemplateDTO actualAlbumTemplateDTO = service.create(albumTemplateDTO);
		
		// Assert
		assertEquals(id, actualAlbumTemplateDTO.getId());
		assertEquals(cover, actualAlbumTemplateDTO.getCover());
		assertEquals(expirationDate, actualAlbumTemplateDTO.getExpirationDate());
		assertEquals(launchDate, actualAlbumTemplateDTO.getLaunchDate());
		assertEquals(name, actualAlbumTemplateDTO.getName());
		assertEquals(numStickers, actualAlbumTemplateDTO.getNumStickers());
		assertEquals(price, actualAlbumTemplateDTO.getPrice());
		
		ArgumentCaptor<Album> albumCaptor = ArgumentCaptor.forClass(Album.class);
		verify(this.albumRepository).save(albumCaptor.capture());
		Album album = albumCaptor.getValue();
		assertEquals(id, album.getAlbumTemplateId());
		assertNull(album.getUserId());
	}
	
	@Test
	public void testEdit() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String cover = "Cover";
		LocalDateTime expirationDate = LocalDateTime.of(2023, 12, 31, 23, 59); 
		LocalDateTime launchDate = LocalDateTime.of(2023, 1, 1, 0, 0); 
		String name = "Name";
		Long numStickers = 10L;
		BigDecimal price = new BigDecimal("10.99");
		AlbumTemplateDTO albumTemplateDTO = AlbumTemplateDTO.builder()
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();
		AlbumTemplate savedAlbumTemplate = AlbumTemplate.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();

		when(repository.existsById(id)).thenReturn(true);
		when(repository.save(any())).thenReturn(savedAlbumTemplate);
		
		// Act
		AlbumTemplateDTO actualAlbumTemplateDTO = service.edit(id, albumTemplateDTO);
		
		// Assert
		assertEquals(id, actualAlbumTemplateDTO.getId());
		assertEquals(cover, actualAlbumTemplateDTO.getCover());
		assertEquals(expirationDate, actualAlbumTemplateDTO.getExpirationDate());
		assertEquals(launchDate, actualAlbumTemplateDTO.getLaunchDate());
		assertEquals(name, actualAlbumTemplateDTO.getName());
		assertEquals(numStickers, actualAlbumTemplateDTO.getNumStickers());
		assertEquals(price, actualAlbumTemplateDTO.getPrice());
	}

	@Test
	public void testEditNotFound() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(repository.existsById(id)).thenReturn(false);
		
		// Act
		assertThrows(EntityNotFoundException.class, () -> service.edit(id, new AlbumTemplateDTO()));
		
		// Assert
		verify(this.repository, never()).save(any());
	}
	
	@Test
	public void testDelete() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(repository.existsById(id)).thenReturn(true);
		
		// Act
		service.delete(id);
		
		// Assert
		verify(this.repository).deleteById(id);
	}

	@Test
	public void testDeleteNotFound() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(repository.existsById(id)).thenReturn(false);
		
		// Act
		assertThrows(EntityNotFoundException.class, () -> service.delete(id));
		
		// Assert
		verify(this.repository, never()).deleteById(any());
	}
}
