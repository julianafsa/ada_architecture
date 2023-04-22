package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.StickerTemplateCreationDTO;
import br.com.ada.stickers.model.dto.StickerTemplateDTO;
import br.com.ada.stickers.model.dto.StickerTemplateUpdateDTO;
import br.com.ada.stickers.model.entity.StickerTemplate;
import br.com.ada.stickers.model.mapper.StickerTemplateMapper;
import br.com.ada.stickers.repository.StickerTemplateRepository;
import br.com.ada.stickers.service.StickerTemplateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StickerTemplateServiceImpl implements StickerTemplateService {

    private final StickerTemplateRepository repository;
    private final StickerTemplateMapper mapper;

    public StickerTemplateServiceImpl(final StickerTemplateRepository repository, final StickerTemplateMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    public List<StickerTemplateDTO> findAll(Optional<String> opAlbumTemplateId) {
    	List<StickerTemplate> stickerTemplates;
    	if (opAlbumTemplateId.isPresent()) {
    		stickerTemplates = repository.findByAlbumTemplateId(opAlbumTemplateId.get());
    	} else {
    		stickerTemplates = repository.findAll();
    	}
        return mapper.parseListDTO(stickerTemplates);
    }

    @Override
    public StickerTemplateDTO findById(final String id) {
        Optional<StickerTemplate> optional = repository.findById(id);
        if (optional.isPresent()) {
            final StickerTemplate entity = optional.get();
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public StickerTemplateDTO create(final StickerTemplateCreationDTO creationDTO) {
        StickerTemplate entity = mapper.parseEntity(creationDTO);
        entity.setId(null);
        entity = repository.save(entity);
        return mapper.parseDTO(entity);
    }

    @Override
    public StickerTemplateDTO edit(final String id, final StickerTemplateUpdateDTO updateDTO) {
        if (repository.existsById(id)) {
            StickerTemplate entity = mapper.parseEntity(updateDTO);
            entity.setId(id);
            entity = repository.save(entity);
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(id);
    }
}
