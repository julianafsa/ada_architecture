package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaUpdateDTO;
import br.com.ada.stickers.model.entity.PrototipoDeFigurinha;
import br.com.ada.stickers.model.mapper.PrototipoDeFigurinhaMapper;
import br.com.ada.stickers.repository.PrototipoDeFigurinhaRepository;
import br.com.ada.stickers.service.PrototipoDeFigurinhaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrototipoDeFigurinhaServiceImpl implements PrototipoDeFigurinhaService {

    private final PrototipoDeFigurinhaRepository repository;
    private final PrototipoDeFigurinhaMapper mapper;

    public PrototipoDeFigurinhaServiceImpl(final PrototipoDeFigurinhaRepository repository, final PrototipoDeFigurinhaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    public List<PrototipoDeFigurinhaDTO> findAll(Optional<String> opAlbumTemplateId) {
    	List<PrototipoDeFigurinha> prototipoDeFigurinhas;
    	if (opAlbumTemplateId.isPresent()) {
    		prototipoDeFigurinhas = repository.findByAlbumTemplateId(opAlbumTemplateId.get());
    	} else {
    		prototipoDeFigurinhas = repository.findAll();
    	}
        return mapper.parseListDTO(prototipoDeFigurinhas);
    }

    @Override
    public PrototipoDeFigurinhaDTO findById(final String id) {
        Optional<PrototipoDeFigurinha> optional = repository.findById(id);
        if (optional.isPresent()) {
            final PrototipoDeFigurinha entity = optional.get();
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public PrototipoDeFigurinhaDTO create(final PrototipoDeFigurinhaCreationDTO creationDTO) {
        PrototipoDeFigurinha entity = mapper.parseEntity(creationDTO);
        entity.setId(null);
        entity = repository.save(entity);
        return mapper.parseDTO(entity);
    }

    @Override
    public PrototipoDeFigurinhaDTO edit(final String id, final PrototipoDeFigurinhaUpdateDTO updateDTO) {
        if (repository.existsById(id)) {
            PrototipoDeFigurinha entity = mapper.parseEntity(updateDTO);
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
