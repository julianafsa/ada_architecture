package br.com.ada.albuns.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import br.com.ada.albuns.model.dto.TransacaoAlbumDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ada.albuns.service.TransacaoAlbumService;

public class TransacaoAlbumControllerTest {
	private TransacaoAlbumController controller;
	private TransacaoAlbumService service;
	
	@BeforeEach
	public void setUp() {
		this.service = mock(TransacaoAlbumService.class);
		this.controller = new TransacaoAlbumController(this.service);
	}

	@Test
	public void testFindAll() {
		// Arrange
		List<TransacaoAlbumDTO> albumJournals = List.of(new TransacaoAlbumDTO());
		when(service.findAll()).thenReturn(albumJournals);
		
		// Act
		ResponseEntity<List<TransacaoAlbumDTO>> response = controller.findAll();
		
		// Assert
		verify(service).findAll();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(albumJournals, response.getBody());
	}

	@Test
	public void testFindAllNoContent() {
		// Arrange
		when(service.findAll()).thenReturn(List.of());
		
		// Act
		ResponseEntity<List<TransacaoAlbumDTO>> response = controller.findAll();
		
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
		ResponseEntity<List<TransacaoAlbumDTO>> response = controller.findAll();
		
		// Assert
		verify(service).findAll();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
