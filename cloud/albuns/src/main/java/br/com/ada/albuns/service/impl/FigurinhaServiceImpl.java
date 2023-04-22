package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.client.FigurinhaClient;
import br.com.ada.albuns.client.FigurinhaPrototipoClient;
import br.com.ada.albuns.client.dto.FigurinhaCreationDTO;
import br.com.ada.albuns.client.dto.FigurinhaDTO;
import br.com.ada.albuns.client.dto.FigurinhaPrototipoDTO;
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
	private final FigurinhaPrototipoClient figurinhaPrototipoClient;
	private final FigurinhaClient figurinhaClient;
	
	public FigurinhaServiceImpl(AlbumRepository albumRepository, FigurinhaPrototipoClient figurinhaPrototipoClient, FigurinhaClient figurinhaClient) {
		this.albumRepository = albumRepository;
		this.figurinhaPrototipoClient = figurinhaPrototipoClient;
		this.figurinhaClient = figurinhaClient;
	}

	@Override
	public boolean createFigurinhasForAlbum(String albumPrototipoId) {
		List<FigurinhaDTO> figurinhasCreated = new ArrayList<>();
		boolean shouldRevertFigurinhasCreation = false;

		try {
			ResponseEntity<List<FigurinhaPrototipoDTO>> figurinhaPrototiposResponse = figurinhaPrototipoClient.findAll(albumPrototipoId);
		    if (!figurinhaPrototiposResponse.getStatusCode().equals(HttpStatus.OK)) {
		    	log.error("Error retrieving figurinha prototipos: {}", figurinhaPrototiposResponse.getStatusCode());
		    	throw new EntityNotFoundException("No figurinha prototipo found for this album prototipo");
		    }
	
		    Album padraoAlbum = albumRepository.findByUsuarioIdAndAlbumPrototipoId(null, albumPrototipoId).orElseThrow(() -> new EntityNotFoundException("Padrao album not found"));
		    List<FigurinhaPrototipoDTO> figurinhaPrototipos = figurinhaPrototiposResponse.getBody();
		    if (figurinhaPrototipos != null) {
		    	for (FigurinhaPrototipoDTO figurinhaPrototipo : figurinhaPrototipos) {
			    	List<FigurinhaDTO> figurinhasCreatedInThisStep = this.createFigurinhas(figurinhaPrototipo, padraoAlbum);
			    	if (figurinhasCreatedInThisStep != null) {
			    		figurinhasCreated.addAll(figurinhasCreatedInThisStep);
			    	} else {
			    		shouldRevertFigurinhasCreation = true;
			    		break;
			    	}
			    }
		    }
		} catch (Exception e) {
			shouldRevertFigurinhasCreation = true;
		}
	    
		if (shouldRevertFigurinhasCreation) {
			this.revertFigurinhasCreation(figurinhasCreated);
			return false;
		}
    	
    	return true;
	}
	
	private List<FigurinhaDTO> createFigurinhas(FigurinhaPrototipoDTO figurinhaPrototipoDTO, Album album) {
		List<FigurinhaDTO> figurinhasCreated = new ArrayList<>();
		boolean shouldRevertFigurinhasCreation = false;
		
		try {
	    	FigurinhaCreationDTO figurinhaCreationDTO = FigurinhaCreationDTO.builder()
	    			.figurinhaPrototipoId(figurinhaPrototipoDTO.getId())
	    			.albumId(album.getId())
	    			.build();
	    	
	    	int quantity = this.calculateQuantityByRaridade(figurinhaPrototipoDTO);
	    	
	    	for (int i = 0; i < quantity; i++) {
	    		
		    	log.info("Creating figurinha {} for {}", i + 1, figurinhaPrototipoDTO.getDescription());
		    	ResponseEntity<FigurinhaDTO> response = figurinhaClient.create(figurinhaCreationDTO);
		    	if (response.getStatusCode().is2xxSuccessful()) {
		    		figurinhasCreated.add(response.getBody());
		    		log.info("Success: {}", response.getStatusCode());
		    	} else {
		    		log.error("Error creating figurinha: {}", response.getStatusCode());
		    		shouldRevertFigurinhasCreation = true;
		    		break;
		    	}
	    	}
		} catch (Exception e) {
			shouldRevertFigurinhasCreation = true;
		}
		
		if (shouldRevertFigurinhasCreation) {
			this.revertFigurinhasCreation(figurinhasCreated);
			return null;
		}
    	
    	return figurinhasCreated;
	}
	
	private int calculateQuantityByRaridade(FigurinhaPrototipoDTO figurinhaPrototipoDTO) {
		return switch(figurinhaPrototipoDTO.getRaridade()) {
			case 1 -> 1;
			case 2 -> 3;
			case 3 -> 6;
			case 4 -> 10;// throw new RuntimeException("Fake exception");
			default -> 0;
		};
	}
	
	private void revertFigurinhasCreation(List<FigurinhaDTO> figurinhasToRevert) {
		figurinhasToRevert.forEach(figurinhaToRevert -> {
			try {
				log.info("Reverting figurinha {}", figurinhaToRevert.getId());
				figurinhaClient.delete(figurinhaToRevert.getId());
			} catch(Exception e) {
				log.error("Error reverting figurinha creation for figurinha {}: {}", figurinhaToRevert.getId(), e.getMessage());
			}
		});
	}
}
