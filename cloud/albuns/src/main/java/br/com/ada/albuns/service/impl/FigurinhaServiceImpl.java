package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.client.FigurinhaClient;
import br.com.ada.albuns.client.PrototipoDeFigurinhaClient;
import br.com.ada.albuns.client.dto.FigurinhaCreationDTO;
import br.com.ada.albuns.client.dto.FigurinhaDTO;
import br.com.ada.albuns.client.dto.PrototipoDeFigurinhaDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.service.FigurinhaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FigurinhaServiceImpl implements FigurinhaService {
	private final AlbumRepository albumRepository;
	private final PrototipoDeFigurinhaClient prototipoDeFigurinhaClient;
	private final FigurinhaClient figurinhaClient;
	
	public FigurinhaServiceImpl(AlbumRepository albumRepository, PrototipoDeFigurinhaClient prototipoDeFigurinhaClient, FigurinhaClient figurinhaClient) {
		this.albumRepository = albumRepository;
		this.prototipoDeFigurinhaClient = prototipoDeFigurinhaClient;
		this.figurinhaClient = figurinhaClient;
	}

	@Override
	public boolean createStickersForAlbum(String albumTemplateId) {
		List<FigurinhaDTO> stickersCreated = new ArrayList<>();
		boolean shouldRevertStickersCreation = false;

		try {
			ResponseEntity<List<PrototipoDeFigurinhaDTO>> stickerTemplatesResponse = prototipoDeFigurinhaClient.findAll(albumTemplateId);
		    if (!stickerTemplatesResponse.getStatusCode().equals(HttpStatus.OK)) {
		    	log.error("Error retrieving sticker templates: {}", stickerTemplatesResponse.getStatusCode());
		    	throw new EntityNotFoundException("No sticker template found for this album template");
		    }
	
		    Album defaultAlbum = albumRepository.findByUserIdAndAlbumTemplateId(null, albumTemplateId).orElseThrow(() -> new EntityNotFoundException("Default album not found"));
		    List<PrototipoDeFigurinhaDTO> stickerTemplates = stickerTemplatesResponse.getBody();
		    if (stickerTemplates != null) {
		    	for (PrototipoDeFigurinhaDTO stickerTemplate : stickerTemplates) {
			    	List<FigurinhaDTO> stickersCreatedInThisStep = this.createStickers(stickerTemplate, defaultAlbum);
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
	
	private List<FigurinhaDTO> createStickers(PrototipoDeFigurinhaDTO prototipoDeFigurinhaDTO, Album album) {
		List<FigurinhaDTO> stickersCreated = new ArrayList<>();
		boolean shouldRevertStickersCreation = false;
		
		try {
	    	FigurinhaCreationDTO figurinhaCreationDTO = FigurinhaCreationDTO.builder()
	    			.stickerTemplateId(prototipoDeFigurinhaDTO.getId())
	    			.albumId(album.getId())
	    			.build();
	    	
	    	int quantity = this.calculateQuantityByRarity(prototipoDeFigurinhaDTO);
	    	
	    	for (int i = 0; i < quantity; i++) {
	    		
		    	log.info("Creating sticker {} for {}", i + 1, prototipoDeFigurinhaDTO.getDescription());
		    	ResponseEntity<FigurinhaDTO> response = figurinhaClient.create(figurinhaCreationDTO);
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
	
	private int calculateQuantityByRarity(PrototipoDeFigurinhaDTO prototipoDeFigurinhaDTO) {
		return switch(prototipoDeFigurinhaDTO.getRarity()) {
			case 1 -> 1;
			case 2 -> 3;
			case 3 -> 6;
			case 4 -> 10;// throw new RuntimeException("Fake exception");
			default -> 0;
		};
	}
	
	private void revertStickersCreation(List<FigurinhaDTO> stickersToRevert) {
		stickersToRevert.forEach(stickerToRevert -> {
			try {
				log.info("Reverting sticker {}", stickerToRevert.getId());
				figurinhaClient.delete(stickerToRevert.getId());
			} catch(Exception e) {
				log.error("Error reverting sticker creation for sticker {}: {}", stickerToRevert.getId(), e.getMessage());
			}
		});
	}
}
