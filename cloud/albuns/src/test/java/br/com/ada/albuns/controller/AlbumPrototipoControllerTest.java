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

import br.com.ada.albuns.model.dto.AlbumTemplateDTO;
import br.com.ada.albuns.service.AlbumTemplateService;
import jakarta.persistence.EntityNotFoundException;

public class AlbumTemplateControllerTest {
	private AlbumTemplateController controller;
	private AlbumTemplateService service;
	
	@BeforeEach
	public void setUp() {
		this.service = mock(AlbumTemplateService.class);
		this.controller = new AlbumTemplateController(this.service);
	}

	@Test
	public void testCreate() {
		// Arrange
		AlbumTemplateDTO albumTemplateDTO = new AlbumTemplateDTO();
		when(service.create(albumTemplateDTO)).thenReturn(albumTemplateDTO);
		
		// Act
		ResponseEntity<AlbumTemplateDTO> response = controller.create(albumTemplateDTO);
		
		// Assert
		verify(service).create(albumTemplateDTO);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(albumTemplateDTO, response.getBody());
	}

	@Test
	public void testCreateException() {
		// Arrange
		AlbumTemplateDTO albumTemplateDTO = new AlbumTemplateDTO();
		when(service.create(albumTemplateDTO)).thenThrow(new RuntimeException());
		
		// Act
		ResponseEntity<AlbumTemplateDTO> response = controller.create(albumTemplateDTO);
		
		// Assert
		verify(service).create(albumTemplateDTO);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}


	@Test
	public void testFindAll() {
		// Arrange
		List<AlbumTemplateDTO> albumTemplatesDTO = List.of(new AlbumTemplateDTO());
		when(service.findAll()).thenReturn(albumTemplatesDTO);
		
		// Act
		ResponseEntity<List<AlbumTemplateDTO>> response = controller.findAll();
		
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
		ResponseEntity<List<AlbumTemplateDTO>> response = controller.findAll();
		
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
		ResponseEntity<List<AlbumTemplateDTO>> response = controller.findAll();
		
		// Assert
		verify(service).findAll();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testFindById() {
		// Arrange
		String id = UUID.randomUUID().toString();
		AlbumTemplateDTO albumTemplateDTO = new AlbumTemplateDTO();
		when(service.findById(id)).thenReturn(albumTemplateDTO);
		
		// Act
		ResponseEntity<AlbumTemplateDTO> response = controller.findById(id);
		
		// Assert
		verify(service).findById(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(albumTemplateDTO, response.getBody());
	}

	@Test
	public void testFindByIdNoContent() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(service.findById(id)).thenThrow(new EntityNotFoundException());
		
		// Act
		ResponseEntity<AlbumTemplateDTO> response = controller.findById(id);
		
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
		ResponseEntity<AlbumTemplateDTO> response = controller.findById(id);
		
		// Assert
		verify(service).findById(id);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testEdit() {
		// Arrange
		String id = UUID.randomUUID().toString();
		AlbumTemplateDTO albumTemplateDTO = new AlbumTemplateDTO();
		AlbumTemplateDTO editedAlbumTemplateDTO = new AlbumTemplateDTO();
		when(service.edit(id, albumTemplateDTO)).thenReturn(editedAlbumTemplateDTO);
		
		// Act
		ResponseEntity<AlbumTemplateDTO> response = controller.edit(id, albumTemplateDTO);
		
		// Assert
		verify(service).edit(id, albumTemplateDTO);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(editedAlbumTemplateDTO, response.getBody());
	}

	@Test
	public void testEditNotFound() {
		// Arrange
		String id = UUID.randomUUID().toString();
		AlbumTemplateDTO albumTemplateDTO = new AlbumTemplateDTO();
		when(service.edit(id, albumTemplateDTO)).thenThrow(new EntityNotFoundException());
		
		// Act
		ResponseEntity<AlbumTemplateDTO> response = controller.edit(id, albumTemplateDTO);
		
		// Assert
		verify(service).edit(id, albumTemplateDTO);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testEditException() {
		// Arrange
		String id = UUID.randomUUID().toString();
		AlbumTemplateDTO albumTemplateDTO = new AlbumTemplateDTO();
		when(service.edit(id, albumTemplateDTO)).thenThrow(new RuntimeException());
		
		// Act
		ResponseEntity<AlbumTemplateDTO> response = controller.edit(id, albumTemplateDTO);
		
		// Assert
		verify(service).edit(id, albumTemplateDTO);
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
