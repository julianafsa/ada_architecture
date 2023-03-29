package br.com.ada.albuns.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.service.AlbumService;
import jakarta.persistence.EntityNotFoundException;

public class AlbumControllerTest {
	private AlbumController controller;
	private AlbumService service;
	
	@BeforeEach
	public void setUp() {
		this.service = mock(AlbumService.class);
		this.controller = new AlbumController(this.service);
	}

	@Test
	public void testCreate() {
		// Arrange
		AlbumDTO albumDTO = new AlbumDTO();
		when(service.create(albumDTO)).thenReturn(albumDTO);
		
		// Act
		ResponseEntity<AlbumDTO> response = controller.create(albumDTO);
		
		// Assert
		verify(service).create(albumDTO);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(albumDTO, response.getBody());
	}

	@Test
	public void testCreateException() {
		// Arrange
		AlbumDTO albumDTO = new AlbumDTO();
		when(service.create(albumDTO)).thenThrow(new RuntimeException());
		
		// Act
		ResponseEntity<AlbumDTO> response = controller.create(albumDTO);
		
		// Assert
		verify(service).create(albumDTO);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}


	@Test
	public void testFindAll() {
		// Arrange
		List<AlbumDTO> albums = List.of(new AlbumDTO());
		when(service.findAll()).thenReturn(albums);
		
		// Act
		ResponseEntity<List<AlbumDTO>> response = controller.findAll();
		
		// Assert
		verify(service).findAll();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(albums, response.getBody());
	}

	@Test
	public void testFindAllNoContent() {
		// Arrange
		when(service.findAll()).thenReturn(List.of());
		
		// Act
		ResponseEntity<List<AlbumDTO>> response = controller.findAll();
		
		// Assert
		verify(service).findAll();
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void testFindAllException() {
		// Arrange
		when(service.findAll()).thenThrow(new RuntimeException());
		
		// Act
		ResponseEntity<List<AlbumDTO>> response = controller.findAll();
		
		// Assert
		verify(service).findAll();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testFindById() {
		// Arrange
		String id = UUID.randomUUID().toString();
		AlbumDTO album = new AlbumDTO();
		when(service.findById(id)).thenReturn(album);
		
		// Act
		ResponseEntity<AlbumDTO> response = controller.findById(id);
		
		// Assert
		verify(service).findById(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(album, response.getBody());
	}

	@Test
	public void testFindByIdNoContent() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(service.findById(id)).thenThrow(new EntityNotFoundException());
		
		// Act
		ResponseEntity<AlbumDTO> response = controller.findById(id);
		
		// Assert
		verify(service).findById(id);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	public void testFindByIdException() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(service.findById(id)).thenThrow(new RuntimeException());
		
		// Act
		ResponseEntity<AlbumDTO> response = controller.findById(id);
		
		// Assert
		verify(service).findById(id);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
}
