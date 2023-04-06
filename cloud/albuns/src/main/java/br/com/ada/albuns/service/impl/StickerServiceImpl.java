package br.com.ada.albuns.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
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
	public boolean createStickersForAlbum(String albumTemplateId) {
		List<StickerDTO> stickersCreated = new ArrayList<>();
		boolean shouldRevertStickersCreation = false;

		try {
			ResponseEntity<List<StickerTemplateDTO>> stickerTemplatesResponse = stickerTemplateClient.findAll(albumTemplateId);
		    if (!stickerTemplatesResponse.getStatusCode().equals(HttpStatus.OK)) {
		    	log.error("Error retrieving sticker templates: {}", stickerTemplatesResponse.getStatusCode());
		    	throw new EntityNotFoundException("No sticker template found for this album template");
		    }
	
		    Album defaultAlbum = albumRepository.findByUserIdAndAlbumTemplateId(null, albumTemplateId).orElseThrow(() -> new EntityNotFoundException("Default album not found"));
		    List<StickerTemplateDTO> stickerTemplates = stickerTemplatesResponse.getBody();
		    if (stickerTemplates != null) {
		    	for (StickerTemplateDTO stickerTemplate : stickerTemplates) {
			    	List<StickerDTO> stickersCreatedInThisStep = this.createStickers(stickerTemplate, defaultAlbum);
			    	if (stickersCreatedInThisStep != null) {
			    		stickersCreated.addAll(stickersCreatedInThisStep);
			    	} else {
			    		shouldRevertStickersCreation = true;
			    		break;
			    	}
			    }
		    }
		} catch (Exception e) {
			shouldRevertStickersCreation = true;
		}
	    
		if (shouldRevertStickersCreation) {
			this.revertStickersCreation(stickersCreated);
			return false;
		}
    	
    	return true;
	}
	
	private List<StickerDTO> createStickers(StickerTemplateDTO stickerTemplateDTO, Album album) {
		List<StickerDTO> stickersCreated = new ArrayList<>();
		boolean shouldRevertStickersCreation = false;
		
		try {
	    	StickerCreationDTO stickerCreationDTO = StickerCreationDTO.builder()
	    			.stickerTemplateId(stickerTemplateDTO.getId())
	    			.albumId(album.getId())
	    			.build();
	    	
	    	int quantity = this.calculateQuantityByRarity(stickerTemplateDTO);
	    	
	    	for (int i = 0; i < quantity; i++) {
	    		
		    	log.info("Creating sticker {} for {}", i + 1, stickerTemplateDTO.getDescription());
		    	ResponseEntity<StickerDTO> response = stickerClient.create(stickerCreationDTO);
		    	if (response.getStatusCode().is2xxSuccessful()) {
		    		stickersCreated.add(response.getBody());
		    		log.info("Success: {}", response.getStatusCode());
		    	} else {
		    		log.error("Error creating sticker: {}", response.getStatusCode());
		    		shouldRevertStickersCreation = true;
		    		break;
		    	}
	    	}
		} catch (Exception e) {
			shouldRevertStickersCreation = true;
		}
		
		if (shouldRevertStickersCreation) {
			this.revertStickersCreation(stickersCreated);
			return null;
		}
    	
    	return stickersCreated;
	}
	
	private int calculateQuantityByRarity(StickerTemplateDTO stickerTemplateDTO) {
		return switch(stickerTemplateDTO.getRarity()) {
			case 1 -> 1;
			case 2 -> 3;
			case 3 -> 6;
			case 4 -> throw new RuntimeException("Fake exception");//10;
			default -> 0;
		};
	}
	
	private void revertStickersCreation(List<StickerDTO> stickersToRevert) {
		stickersToRevert.forEach(stickerToRevert -> {
			try {
				log.info("Reverting sticker {}", stickerToRevert.getId());
				stickerClient.delete(stickerToRevert.getId());
			} catch(Exception e) {
				log.error("Error reverting sticker creation for sticker {}: {}", stickerToRevert.getId(), e.getMessage());
			}
		});
	}
}
