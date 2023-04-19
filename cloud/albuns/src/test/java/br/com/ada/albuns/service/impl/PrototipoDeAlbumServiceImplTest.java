package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.model.dto.PrototipoDeAlbumDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.entity.PrototipoDeAlbum;
import br.com.ada.albuns.model.mapper.PrototipoDeAlbumMapper;
import br.com.ada.albuns.model.mapper.PrototipoDeAlbumMapperImpl;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.repository.PrototipoDeAlbumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PrototipoDeAlbumServiceImplTest {
	private PrototipoDeAlbumRepository repository;
	private PrototipoDeAlbumMapper mapper;
	private AlbumRepository albumRepository;
	private PrototipoDeAlbumServiceImpl service;

	@BeforeEach
	public void setUp() {
		this.repository = mock(PrototipoDeAlbumRepository.class);
		this.mapper = new PrototipoDeAlbumMapperImpl();
		this.albumRepository = mock(AlbumRepository.class);
		this.service = new PrototipoDeAlbumServiceImpl(repository, mapper, albumRepository);
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
		PrototipoDeAlbum prototipoDeAlbum = PrototipoDeAlbum.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();
		when(repository.findAll()).thenReturn(List.of(prototipoDeAlbum));
		
		// Act
		List<PrototipoDeAlbumDTO> actualAlbumTemplates = service.findAll();
		
		// Assert
		assertEquals(1, actualAlbumTemplates.size());
		PrototipoDeAlbumDTO actualAlbumTemplate = actualAlbumTemplates.get(0);
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
		PrototipoDeAlbum prototipoDeAlbum = PrototipoDeAlbum.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();
		when(repository.findById(id)).thenReturn(Optional.of(prototipoDeAlbum));
		
		// Act
		PrototipoDeAlbumDTO actualAlbumTemplate = service.findById(id);
		
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
		PrototipoDeAlbumDTO prototipoDeAlbumDTO = br.com.ada.albuns.model.dto.PrototipoDeAlbumDTO.builder()
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();
		PrototipoDeAlbum savedPrototipoDeAlbum = PrototipoDeAlbum.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();

		when(repository.save(any())).thenReturn(savedPrototipoDeAlbum);
		
		// Act
		PrototipoDeAlbumDTO actualPrototipoDeAlbumDTO = service.create(prototipoDeAlbumDTO);
		
		// Assert
		assertEquals(id, actualPrototipoDeAlbumDTO.getId());
		assertEquals(cover, actualPrototipoDeAlbumDTO.getCover());
		assertEquals(expirationDate, actualPrototipoDeAlbumDTO.getExpirationDate());
		assertEquals(launchDate, actualPrototipoDeAlbumDTO.getLaunchDate());
		assertEquals(name, actualPrototipoDeAlbumDTO.getName());
		assertEquals(numStickers, actualPrototipoDeAlbumDTO.getNumStickers());
		assertEquals(price, actualPrototipoDeAlbumDTO.getPrice());
		
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
		PrototipoDeAlbumDTO prototipoDeAlbumDTO = br.com.ada.albuns.model.dto.PrototipoDeAlbumDTO.builder()
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();
		PrototipoDeAlbum savedPrototipoDeAlbum = PrototipoDeAlbum.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numStickers(numStickers)
				.price(price)
				.build();

		when(repository.existsById(id)).thenReturn(true);
		when(repository.save(any())).thenReturn(savedPrototipoDeAlbum);
		
		// Act
		PrototipoDeAlbumDTO actualPrototipoDeAlbumDTO = service.edit(id, prototipoDeAlbumDTO);
		
		// Assert
		assertEquals(id, actualPrototipoDeAlbumDTO.getId());
		assertEquals(cover, actualPrototipoDeAlbumDTO.getCover());
		assertEquals(expirationDate, actualPrototipoDeAlbumDTO.getExpirationDate());
		assertEquals(launchDate, actualPrototipoDeAlbumDTO.getLaunchDate());
		assertEquals(name, actualPrototipoDeAlbumDTO.getName());
		assertEquals(numStickers, actualPrototipoDeAlbumDTO.getNumStickers());
		assertEquals(price, actualPrototipoDeAlbumDTO.getPrice());
	}

	@Test
	public void testEditNotFound() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(repository.existsById(id)).thenReturn(false);
		
		// Act
		assertThrows(EntityNotFoundException.class, () -> service.edit(id, new PrototipoDeAlbumDTO()));
		
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
