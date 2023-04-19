package br.com.ada.albuns.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import br.com.ada.albuns.model.dto.PrototipoDeAlbumDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ada.albuns.service.PrototipoDeAlbumService;
import jakarta.persistence.EntityNotFoundException;

public class PrototipoDeAlbumControllerTest {
	private PrototipoDeAlbumController controller;
	private PrototipoDeAlbumService service;
	
	@BeforeEach
	public void setUp() {
		this.service = mock(PrototipoDeAlbumService.class);
		this.controller = new PrototipoDeAlbumController(this.service);
	}

	@Test
	public void testCreate() {
		// Arrange
		PrototipoDeAlbumDTO prototipoDeAlbumDTO = new PrototipoDeAlbumDTO();
		when(service.create(prototipoDeAlbumDTO)).thenReturn(prototipoDeAlbumDTO);
		
		// Act
		ResponseEntity<PrototipoDeAlbumDTO> response = controller.create(prototipoDeAlbumDTO);
		
		// Assert
		verify(service).create(prototipoDeAlbumDTO);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(prototipoDeAlbumDTO, response.getBody());
	}

	@Test
	public void testCreateException() {
		// Arrange
		PrototipoDeAlbumDTO prototipoDeAlbumDTO = new PrototipoDeAlbumDTO();
		when(service.create(prototipoDeAlbumDTO)).thenThrow(new RuntimeException());
		
		// Act
		ResponseEntity<PrototipoDeAlbumDTO> response = controller.create(prototipoDeAlbumDTO);
		
		// Assert
		verify(service).create(prototipoDeAlbumDTO);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}


	@Test
	public void testFindAll() {
		// Arrange
		List<PrototipoDeAlbumDTO> albumTemplatesDTO = List.of(new PrototipoDeAlbumDTO());
		when(service.findAll()).thenReturn(albumTemplatesDTO);
		
		// Act
		ResponseEntity<List<PrototipoDeAlbumDTO>> response = controller.findAll();
		
		// Assert
		verify(service).findAll();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(albumTemplatesDTO, response.getBody());
	}

	@Test
	public void testFindAllNoContent() {
		// Arrange
		when(service.findAll()).thenReturn(List.of());
		
		// Act
		ResponseEntity<List<PrototipoDeAlbumDTO>> response = controller.findAll();
		
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
		ResponseEntity<List<PrototipoDeAlbumDTO>> response = controller.findAll();
		
		// Assert
		verify(service).findAll();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testFindById() {
		// Arrange
		String id = UUID.randomUUID().toString();
		PrototipoDeAlbumDTO prototipoDeAlbumDTO = new PrototipoDeAlbumDTO();
		when(service.findById(id)).thenReturn(prototipoDeAlbumDTO);
		
		// Act
		ResponseEntity<PrototipoDeAlbumDTO> response = controller.findById(id);
		
		// Assert
		verify(service).findById(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(prototipoDeAlbumDTO, response.getBody());
	}

	@Test
	public void testFindByIdNoContent() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(service.findById(id)).thenThrow(new EntityNotFoundException());
		
		// Act
		ResponseEntity<PrototipoDeAlbumDTO> response = controller.findById(id);
		
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
		ResponseEntity<PrototipoDeAlbumDTO> response = controller.findById(id);
		
		// Assert
		verify(service).findById(id);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testEdit() {
		// Arrange
		String id = UUID.randomUUID().toString();
		PrototipoDeAlbumDTO prototipoDeAlbumDTO = new PrototipoDeAlbumDTO();
		PrototipoDeAlbumDTO editedPrototipoDeAlbumDTO = new PrototipoDeAlbumDTO();
		when(service.edit(id, prototipoDeAlbumDTO)).thenReturn(editedPrototipoDeAlbumDTO);
		
		// Act
		ResponseEntity<PrototipoDeAlbumDTO> response = controller.edit(id, prototipoDeAlbumDTO);
		
		// Assert
		verify(service).edit(id, prototipoDeAlbumDTO);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(editedPrototipoDeAlbumDTO, response.getBody());
	}

	@Test
	public void testEditNotFound() {
		// Arrange
		String id = UUID.randomUUID().toString();
		PrototipoDeAlbumDTO prototipoDeAlbumDTO = new PrototipoDeAlbumDTO();
		when(service.edit(id, prototipoDeAlbumDTO)).thenThrow(new EntityNotFoundException());
		
		// Act
		ResponseEntity<PrototipoDeAlbumDTO> response = controller.edit(id, prototipoDeAlbumDTO);
		
		// Assert
		verify(service).edit(id, prototipoDeAlbumDTO);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testEditException() {
		// Arrange
		String id = UUID.randomUUID().toString();
		PrototipoDeAlbumDTO prototipoDeAlbumDTO = new PrototipoDeAlbumDTO();
		when(service.edit(id, prototipoDeAlbumDTO)).thenThrow(new RuntimeException());
		
		// Act
		ResponseEntity<PrototipoDeAlbumDTO> response = controller.edit(id, prototipoDeAlbumDTO);
		
		// Assert
		verify(service).edit(id, prototipoDeAlbumDTO);
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
