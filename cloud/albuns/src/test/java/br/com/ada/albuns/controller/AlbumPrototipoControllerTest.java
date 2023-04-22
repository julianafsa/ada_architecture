package br.com.ada.albuns.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ada.albuns.model.dto.AlbumPrototipoDTO;
import br.com.ada.albuns.service.AlbumPrototipoService;
import jakarta.persistence.EntityNotFoundException;

public class AlbumPrototipoControllerTest {
	private AlbumPrototipoController controller;
	private AlbumPrototipoService service;
	
	@BeforeEach
	public void setUp() {
		this.service = mock(AlbumPrototipoService.class);
		this.controller = new AlbumPrototipoController(this.service);
	}

	@Test
	public void testCreate() {
		// Arrange
		AlbumPrototipoDTO albumPrototipoDTO = new AlbumPrototipoDTO();
		when(service.create(albumPrototipoDTO)).thenReturn(albumPrototipoDTO);
		
		// Act
		ResponseEntity<AlbumPrototipoDTO> response = controller.create(albumPrototipoDTO);
		
		// Assert
		verify(service).create(albumPrototipoDTO);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(albumPrototipoDTO, response.getBody());
	}

	@Test
	public void testCreateException() {
		// Arrange
		AlbumPrototipoDTO albumPrototipoDTO = new AlbumPrototipoDTO();
		when(service.create(albumPrototipoDTO)).thenThrow(new RuntimeException());
		
		// Act
		ResponseEntity<AlbumPrototipoDTO> response = controller.create(albumPrototipoDTO);
		
		// Assert
		verify(service).create(albumPrototipoDTO);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}


	@Test
	public void testFindAll() {
		// Arrange
		List<AlbumPrototipoDTO> albumPrototiposDTO = List.of(new AlbumPrototipoDTO());
		when(service.findAll()).thenReturn(albumPrototiposDTO);
		
		// Act
		ResponseEntity<List<AlbumPrototipoDTO>> response = controller.findAll();
		
		// Assert
		verify(service).findAll();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(albumPrototiposDTO, response.getBody());
	}

	@Test
	public void testFindAllNoContent() {
		// Arrange
		when(service.findAll()).thenReturn(List.of());
		
		// Act
		ResponseEntity<List<AlbumPrototipoDTO>> response = controller.findAll();
		
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
		ResponseEntity<List<AlbumPrototipoDTO>> response = controller.findAll();
		
		// Assert
		verify(service).findAll();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testFindById() {
		// Arrange
		String id = UUID.randomUUID().toString();
		AlbumPrototipoDTO albumPrototipoDTO = new AlbumPrototipoDTO();
		when(service.findById(id)).thenReturn(albumPrototipoDTO);
		
		// Act
		ResponseEntity<AlbumPrototipoDTO> response = controller.findById(id);
		
		// Assert
		verify(service).findById(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(albumPrototipoDTO, response.getBody());
	}

	@Test
	public void testFindByIdNoContent() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(service.findById(id)).thenThrow(new EntityNotFoundException());
		
		// Act
		ResponseEntity<AlbumPrototipoDTO> response = controller.findById(id);
		
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
		ResponseEntity<AlbumPrototipoDTO> response = controller.findById(id);
		
		// Assert
		verify(service).findById(id);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testEdit() {
		// Arrange
		String id = UUID.randomUUID().toString();
		AlbumPrototipoDTO albumPrototipoDTO = new AlbumPrototipoDTO();
		AlbumPrototipoDTO editedAlbumPrototipoDTO = new AlbumPrototipoDTO();
		when(service.edit(id, albumPrototipoDTO)).thenReturn(editedAlbumPrototipoDTO);
		
		// Act
		ResponseEntity<AlbumPrototipoDTO> response = controller.edit(id, albumPrototipoDTO);
		
		// Assert
		verify(service).edit(id, albumPrototipoDTO);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(editedAlbumPrototipoDTO, response.getBody());
	}

	@Test
	public void testEditNotFound() {
		// Arrange
		String id = UUID.randomUUID().toString();
		AlbumPrototipoDTO albumPrototipoDTO = new AlbumPrototipoDTO();
		when(service.edit(id, albumPrototipoDTO)).thenThrow(new EntityNotFoundException());
		
		// Act
		ResponseEntity<AlbumPrototipoDTO> response = controller.edit(id, albumPrototipoDTO);
		
		// Assert
		verify(service).edit(id, albumPrototipoDTO);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testEditException() {
		// Arrange
		String id = UUID.randomUUID().toString();
		AlbumPrototipoDTO albumPrototipoDTO = new AlbumPrototipoDTO();
		when(service.edit(id, albumPrototipoDTO)).thenThrow(new RuntimeException());
		
		// Act
		ResponseEntity<AlbumPrototipoDTO> response = controller.edit(id, albumPrototipoDTO);
		
		// Assert
		verify(service).edit(id, albumPrototipoDTO);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void testDelete() {
		// Arrange
		String id = UUID.randomUUID().toString();
		
		// Act
		ResponseEntity<Object> response = controller.delete(id);
		
		// Assert
		verify(service).delete(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testeDeleteNotFound() {
		// Arrange
		String id = UUID.randomUUID().toString();
		doThrow(new EntityNotFoundException()).when(service).delete(id);
		
		// Act
		ResponseEntity<Object> response = controller.delete(id);
		
		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testDeleteException() {
		// Arrange
		String id = UUID.randomUUID().toString();
		doThrow(new RuntimeException()).when(service).delete(id);
		
		// Act
		ResponseEntity<Object> response = controller.delete(id);
		
		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
