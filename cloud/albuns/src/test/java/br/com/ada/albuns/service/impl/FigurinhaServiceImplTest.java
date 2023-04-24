package br.com.ada.albuns.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import br.com.ada.albuns.client.FigurinhaClient;
import br.com.ada.albuns.client.FigurinhaPrototipoClient;
import br.com.ada.albuns.client.dto.FigurinhaPrototipoDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.repository.AlbumRepository;

public class FigurinhaServiceImplTest {
	private AlbumRepository albumRepository;
	private FigurinhaPrototipoClient figurinhaPrototipoClient;
	private FigurinhaClient figurinhaClient;
	private FigurinhaServiceImpl service;
	
	@BeforeEach
	public void setUp() {
		this.albumRepository = mock(AlbumRepository.class);
		this.figurinhaPrototipoClient = mock(FigurinhaPrototipoClient.class);
		this.figurinhaClient = mock(FigurinhaClient.class);
		this.service = new FigurinhaServiceImpl(albumRepository, figurinhaPrototipoClient, figurinhaClient);
	}
	
	@Test
	public void testCreateFigurinhasForAlbum() {
		// Arrange
		String albumPrototipoId = UUID.randomUUID().toString();
		String padraoAlbumId = UUID.randomUUID().toString();
		FigurinhaPrototipoDTO figurinhaPrototipoDTO = FigurinhaPrototipoDTO.builder()
				.albumPrototipoId(albumPrototipoId)
				.raridade(1)
				.build();
		List<FigurinhaPrototipoDTO> figurinhaPrototiposDTO = List.of(figurinhaPrototipoDTO);
		ResponseEntity<List<FigurinhaPrototipoDTO>> response = ResponseEntity.ok(figurinhaPrototiposDTO);
		Album padraoAlbum = Album.builder()
				.id(padraoAlbumId)
				.build();
		
		when(figurinhaPrototipoClient.findAll(albumPrototipoId)).thenReturn(response);
		when(albumRepository.findByUsuarioIdAndAlbumPrototipoId(null, albumPrototipoId)).thenReturn(Optional.of(padraoAlbum));
		when(figurinhaClient.create(any())).thenReturn(ResponseEntity.ok().build());
		
		// Act
		this.service.createFigurinhasForAlbum(albumPrototipoId);
		
		// Assert
		verify(figurinhaClient).create(any());
	}

}
