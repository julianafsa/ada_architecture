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

import br.com.ada.albuns.model.dto.AlbumPrototipoDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.entity.AlbumPrototipo;
import br.com.ada.albuns.model.mapper.AlbumPrototipoMapper;
import br.com.ada.albuns.model.mapper.AlbumPrototipoMapperImpl;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.repository.AlbumPrototipoRepository;
import jakarta.persistence.EntityNotFoundException;

public class AlbumPrototipoServiceImplTest {
	private AlbumPrototipoRepository repository;
	private AlbumPrototipoMapper mapper;
	private AlbumRepository albumRepository;
	private AlbumPrototipoServiceImpl service;

	@BeforeEach
	public void setUp() {
		this.repository = mock(AlbumPrototipoRepository.class);
		this.mapper = new AlbumPrototipoMapperImpl();
		this.albumRepository = mock(AlbumRepository.class);
		this.service = new AlbumPrototipoServiceImpl(repository, mapper, albumRepository);
	}
	
	@Test
	public void testFindAll() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String cover = "Cover";
		LocalDateTime expirationDate = LocalDateTime.of(2023, 12, 31, 23, 59); 
		LocalDateTime launchDate = LocalDateTime.of(2023, 1, 1, 0, 0); 
		String name = "Name";
		Long numFigurinhas = 10L;
		BigDecimal price = new BigDecimal("10.99");
		AlbumPrototipo albumPrototipo = AlbumPrototipo.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numFigurinhas(numFigurinhas)
				.price(price)
				.build();
		when(repository.findAll()).thenReturn(List.of(albumPrototipo));
		
		// Act
		List<AlbumPrototipoDTO> actualAlbumPrototipos = service.findAll();
		
		// Assert
		assertEquals(1, actualAlbumPrototipos.size());
		AlbumPrototipoDTO actualAlbumPrototipo = actualAlbumPrototipos.get(0);
		assertEquals(id, actualAlbumPrototipo.getId());
		assertEquals(cover, actualAlbumPrototipo.getCover());
		assertEquals(expirationDate, actualAlbumPrototipo.getExpirationDate());
		assertEquals(launchDate, actualAlbumPrototipo.getLaunchDate());
		assertEquals(name, actualAlbumPrototipo.getName());
		assertEquals(numFigurinhas, actualAlbumPrototipo.getNumFigurinhas());
		assertEquals(price, actualAlbumPrototipo.getPrice());
	}

	@Test
	public void testFindById() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String cover = "Cover";
		LocalDateTime expirationDate = LocalDateTime.of(2023, 12, 31, 23, 59); 
		LocalDateTime launchDate = LocalDateTime.of(2023, 1, 1, 0, 0); 
		String name = "Name";
		Long numFigurinhas = 10L;
		BigDecimal price = new BigDecimal("10.99");
		AlbumPrototipo albumPrototipo = AlbumPrototipo.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numFigurinhas(numFigurinhas)
				.price(price)
				.build();
		when(repository.findById(id)).thenReturn(Optional.of(albumPrototipo));
		
		// Act
		AlbumPrototipoDTO actualAlbumPrototipo = service.findById(id);
		
		// Assert
		assertEquals(id, actualAlbumPrototipo.getId());
		assertEquals(cover, actualAlbumPrototipo.getCover());
		assertEquals(expirationDate, actualAlbumPrototipo.getExpirationDate());
		assertEquals(launchDate, actualAlbumPrototipo.getLaunchDate());
		assertEquals(name, actualAlbumPrototipo.getName());
		assertEquals(numFigurinhas, actualAlbumPrototipo.getNumFigurinhas());
		assertEquals(price, actualAlbumPrototipo.getPrice());
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
		Long numFigurinhas = 10L;
		BigDecimal price = new BigDecimal("10.99");
		AlbumPrototipoDTO albumPrototipoDTO = AlbumPrototipoDTO.builder()
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numFigurinhas(numFigurinhas)
				.price(price)
				.build();
		AlbumPrototipo savedAlbumPrototipo = AlbumPrototipo.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numFigurinhas(numFigurinhas)
				.price(price)
				.build();

		when(repository.save(any())).thenReturn(savedAlbumPrototipo);
		
		// Act
		AlbumPrototipoDTO actualAlbumPrototipoDTO = service.create(albumPrototipoDTO);
		
		// Assert
		assertEquals(id, actualAlbumPrototipoDTO.getId());
		assertEquals(cover, actualAlbumPrototipoDTO.getCover());
		assertEquals(expirationDate, actualAlbumPrototipoDTO.getExpirationDate());
		assertEquals(launchDate, actualAlbumPrototipoDTO.getLaunchDate());
		assertEquals(name, actualAlbumPrototipoDTO.getName());
		assertEquals(numFigurinhas, actualAlbumPrototipoDTO.getNumFigurinhas());
		assertEquals(price, actualAlbumPrototipoDTO.getPrice());
		
		ArgumentCaptor<Album> albumCaptor = ArgumentCaptor.forClass(Album.class);
		verify(this.albumRepository).save(albumCaptor.capture());
		Album album = albumCaptor.getValue();
		assertEquals(id, album.getAlbumPrototipoId());
		assertNull(album.getUsuarioId());
	}
	
	@Test
	public void testEdit() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String cover = "Cover";
		LocalDateTime expirationDate = LocalDateTime.of(2023, 12, 31, 23, 59); 
		LocalDateTime launchDate = LocalDateTime.of(2023, 1, 1, 0, 0); 
		String name = "Name";
		Long numFigurinhas = 10L;
		BigDecimal price = new BigDecimal("10.99");
		AlbumPrototipoDTO albumPrototipoDTO = AlbumPrototipoDTO.builder()
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numFigurinhas(numFigurinhas)
				.price(price)
				.build();
		AlbumPrototipo savedAlbumPrototipo = AlbumPrototipo.builder()
				.id(id)
				.cover(cover)
				.expirationDate(expirationDate)
				.launchDate(launchDate)
				.name(name)
				.numFigurinhas(numFigurinhas)
				.price(price)
				.build();

		when(repository.existsById(id)).thenReturn(true);
		when(repository.save(any())).thenReturn(savedAlbumPrototipo);
		
		// Act
		AlbumPrototipoDTO actualAlbumPrototipoDTO = service.edit(id, albumPrototipoDTO);
		
		// Assert
		assertEquals(id, actualAlbumPrototipoDTO.getId());
		assertEquals(cover, actualAlbumPrototipoDTO.getCover());
		assertEquals(expirationDate, actualAlbumPrototipoDTO.getExpirationDate());
		assertEquals(launchDate, actualAlbumPrototipoDTO.getLaunchDate());
		assertEquals(name, actualAlbumPrototipoDTO.getName());
		assertEquals(numFigurinhas, actualAlbumPrototipoDTO.getNumFigurinhas());
		assertEquals(price, actualAlbumPrototipoDTO.getPrice());
	}

	@Test
	public void testEditNotFound() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(repository.existsById(id)).thenReturn(false);
		
		// Act
		assertThrows(EntityNotFoundException.class, () -> service.edit(id, new AlbumPrototipoDTO()));
		
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
