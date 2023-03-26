package br.com.ada.albuns.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.ada.albuns.client.StickerClient;
import br.com.ada.albuns.client.StickerTemplateClient;
import br.com.ada.albuns.client.dto.StickerCreationDTO;
import br.com.ada.albuns.client.dto.StickerDTO;
import br.com.ada.albuns.client.dto.StickerTemplateDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.service.StickerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StickerServiceImpl implements StickerService {
	private final AlbumRepository albumRepository;
	private final StickerTemplateClient stickerTemplateClient;
	private final StickerClient stickerClient;
	
	public StickerServiceImpl(AlbumRepository albumRepository, StickerTemplateClient stickerTemplateClient, StickerClient stickerClient) {
		this.albumRepository = albumRepository;
		this.stickerTemplateClient = stickerTemplateClient;
		this.stickerClient = stickerClient;
	}

	@Override
	public void createStickersForAlbum(String albumTemplateId) {
	    ResponseEntity<List<StickerTemplateDTO>> stickerTemplatesResponse = stickerTemplateClient.findAll(albumTemplateId);
	    if (!stickerTemplatesResponse.getStatusCode().is2xxSuccessful()) {
	    	log.error("Error retrieving sticker templates: {}", stickerTemplatesResponse.getStatusCode());
	    }

	    Album defaultAlbum = albumRepository.findByUserIdAndAlbumTemplateId(null, albumTemplateId).orElseThrow(() -> new EntityNotFoundException());
	    List<StickerTemplateDTO> stickerTemplates = stickerTemplatesResponse.getBody();
	    if (stickerTemplates != null) {
		    stickerTemplates.forEach(stickerTemplate -> {
		    	this.createStickers(stickerTemplate, defaultAlbum);
		    });
	    }
	}
	
	private void createStickers(StickerTemplateDTO stickerTemplateDTO, Album album) {
    	StickerCreationDTO stickerCreationDTO = StickerCreationDTO.builder()
    			.stickerTemplateId(stickerTemplateDTO.getId())
    			.albumId(album.getId())
    			.build();
    	
    	int quantity = this.calculateQuantityByRarity(stickerTemplateDTO);
    	
    	for (int i = 0; i < quantity; i++) {
    		
	    	log.info("Creating sticker {} for {}", i + 1, stickerTemplateDTO.getDescription());
	    	ResponseEntity<StickerDTO> response = stickerClient.create(stickerCreationDTO);
	    	if (response.getStatusCode().is2xxSuccessful()) {
	    		log.info("Success: {}", response.getStatusCode());
	    	} else {
	    		log.error("Error creating sticker: {}", response.getStatusCode());
	    	}
    	}
	}
	
	private int calculateQuantityByRarity(StickerTemplateDTO stickerTemplateDTO) {
		return switch(stickerTemplateDTO.getRarity()) {
			case 1 -> 1;
			case 2 -> 3;
			case 3 -> 6;
			case 4 -> 10;
			default -> 0;
		};
	}
}
