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

import br.com.ada.albuns.client.StickerClient;
import br.com.ada.albuns.client.StickerTemplateClient;
import br.com.ada.albuns.client.dto.StickerTemplateDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.repository.AlbumRepository;

public class StickerServiceImplTest {
	private AlbumRepository albumRepository;
	private StickerTemplateClient stickerTemplateClient;
	private StickerClient stickerClient;
	private StickerServiceImpl service;
	
	@BeforeEach
	public void setUp() {
		this.albumRepository = mock(AlbumRepository.class);
		this.stickerTemplateClient = mock(StickerTemplateClient.class);
		this.stickerClient = mock(StickerClient.class);
		this.service = new StickerServiceImpl(albumRepository, stickerTemplateClient, stickerClient);
	}
	
	@Test
	public void testCreateStickersForAlbum() {
		// Arrange
		String albumTemplateId = UUID.randomUUID().toString();
		String defaultAlbumId = UUID.randomUUID().toString();
		StickerTemplateDTO stickerTemplateDTO = StickerTemplateDTO.builder()
				.albumTemplateId(albumTemplateId)
				.rarity(1)
				.build();
		List<StickerTemplateDTO> stickerTemplatesDTO = List.of(stickerTemplateDTO);
		ResponseEntity<List<StickerTemplateDTO>> response = ResponseEntity.ok(stickerTemplatesDTO);
		Album defaultAlbum = Album.builder()
				.id(defaultAlbumId)
				.build();
		
		when(stickerTemplateClient.findAll(albumTemplateId)).thenReturn(response);
		when(albumRepository.findByUserIdAndAlbumTemplateId(null, albumTemplateId)).thenReturn(Optional.of(defaultAlbum));
		when(stickerClient.create(any())).thenReturn(ResponseEntity.ok().build());
		
		// Act
		this.service.createStickersForAlbum(albumTemplateId);
		
		// Assert
		verify(stickerClient).create(any());
	}

}
