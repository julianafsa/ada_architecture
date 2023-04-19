package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.client.FigurinhaClient;
import br.com.ada.albuns.client.PrototipoDeFigurinhaClient;
import br.com.ada.albuns.client.dto.PrototipoDeFigurinhaDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.repository.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FigurinhaServiceImplTest {
	private AlbumRepository albumRepository;
	private PrototipoDeFigurinhaClient prototipoDeFigurinhaClient;
	private FigurinhaClient figurinhaClient;
	private FigurinhaServiceImpl service;
	
	@BeforeEach
	public void setUp() {
		this.albumRepository = mock(AlbumRepository.class);
		this.prototipoDeFigurinhaClient = mock(PrototipoDeFigurinhaClient.class);
		this.figurinhaClient = mock(FigurinhaClient.class);
		this.service = new FigurinhaServiceImpl(albumRepository, prototipoDeFigurinhaClient, figurinhaClient);
	}
	
	@Test
	public void testCreateStickersForAlbum() {
		// Arrange
		String albumTemplateId = UUID.randomUUID().toString();
		String defaultAlbumId = UUID.randomUUID().toString();
		PrototipoDeFigurinhaDTO prototipoDeFigurinhaDTO = PrototipoDeFigurinhaDTO.builder()
				.albumTemplateId(albumTemplateId)
				.rarity(1)
				.build();
		List<PrototipoDeFigurinhaDTO> stickerTemplatesDTO = List.of(prototipoDeFigurinhaDTO);
		ResponseEntity<List<PrototipoDeFigurinhaDTO>> response = ResponseEntity.ok(stickerTemplatesDTO);
		Album defaultAlbum = Album.builder()
				.id(defaultAlbumId)
				.build();
		
		when(prototipoDeFigurinhaClient.findAll(albumTemplateId)).thenReturn(response);
		when(albumRepository.findByUserIdAndAlbumTemplateId(null, albumTemplateId)).thenReturn(Optional.of(defaultAlbum));
		when(figurinhaClient.create(any())).thenReturn(ResponseEntity.ok().build());
		
		// Act
		this.service.createStickersForAlbum(albumTemplateId);
		
		// Assert
		verify(figurinhaClient).create(any());
	}

}
